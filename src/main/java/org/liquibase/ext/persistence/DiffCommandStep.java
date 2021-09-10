package org.liquibase.ext.persistence;

import liquibase.Scope;
import liquibase.command.*;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.ui.UIService;
import org.liquibase.ext.persistence.titan.JDBCHelper;
import org.liquibase.ext.persistence.titan.TitanPort;
import org.liquibase.ext.persistence.titan.TitanVolume;
import org.openapitools.client.model.Repository;
import org.openapitools.client.model.Volume;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class DiffCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "data", "diff" };
    public static final CommandArgumentDefinition<String> SOURCE_DB;
    public static final CommandArgumentDefinition<String> SOURCE_STAGE;
    public static final CommandArgumentDefinition<String> TARGET_STAGE;

    //Copied from  liquibase-core/src/main/java/liquibase/command/core/DiffCommandStep.java
    public static final CommandArgumentDefinition<String> REFERENCE_USERNAME_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> REFERENCE_URL_ARG;
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> URL_ARG;
    public static final CommandArgumentDefinition<String> EXCLUDE_OBJECTS_ARG;
    public static final CommandArgumentDefinition<String> INCLUDE_OBJECTS_ARG;
    public static final CommandArgumentDefinition<String> SCHEMAS_ARG;
    public static final CommandArgumentDefinition<String> DIFF_TYPES_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_PROPERTIES_FILE_ARG;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        SOURCE_DB = builder.argument("sourceDb", String.class)
                .description("Name of Source Database")
                .build();
        SOURCE_STAGE = builder.argument("sourceState", String.class)
                .description("Commit for Source Database, defaults to current (optional)")
                .build();
        TARGET_STAGE = builder.argument("targetState", String.class)
                .description("Commit for Target Database, defaults to current (optional)")
                .build();

        //Copied from  liquibase-core/src/main/java/liquibase/command/core/DiffCommandStep.java
        REFERENCE_URL_ARG = builder.argument("referenceUrl", String.class).required()
                .description("The JDBC reference database connection URL").build();
        REFERENCE_USERNAME_ARG = builder.argument("referenceUsername", String.class)
                .description("The reference database username").build();
        REFERENCE_PASSWORD_ARG = builder.argument("referencePassword", String.class)
                .description("The reference database password")
                .setValueObfuscator(ConfigurationValueObfuscator.STANDARD)
                .build();
        URL_ARG = builder.argument("url", String.class).required()
                .description("The JDBC target database connection URL").build();
        USERNAME_ARG = builder.argument("username", String.class)
                .description("The target database username").build();
        PASSWORD_ARG = builder.argument("password", String.class)
                .description("The target database password")
                .setValueObfuscator(ConfigurationValueObfuscator.STANDARD)
                .build();
        DRIVER_ARG = builder.argument("driver", String.class)
                .description("The JDBC driver class").build();
        DRIVER_PROPERTIES_FILE_ARG = builder.argument("driverPropertiesFile", String.class)
                .description("The JDBC driver properties file").build();
        EXCLUDE_OBJECTS_ARG = builder.argument("excludeObjects", String.class)
                .description("Objects to exclude from diff").build();
        INCLUDE_OBJECTS_ARG = builder.argument("includeObjects", String.class)
                .description("Objects to include in diff").build();
        SCHEMAS_ARG = builder.argument("schemas", String.class)
                .description("Schemas to include in diff").build();
        DIFF_TYPES_ARG = builder.argument("diffTypes", String.class)
                .description("Types of objects to compare").build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        //commandDefinition.setShortDescription("");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        String targetState = resultsBuilder.getCommandScope().getArgumentValue(TARGET_STAGE);
        String sourceDB = resultsBuilder.getCommandScope().getArgumentValue(SOURCE_DB);
        String sourceState = resultsBuilder.getCommandScope().getArgumentValue(SOURCE_STAGE);

        Repository repo = GetRepoInfo(sourceDB);
        String pwd = System.getProperty("user.dir");
        String targetName = "";
        String sp = System.getProperty("file.separator");

        if (sourceDB != null && targetState != null) {
            if (sourceState == null) {
                CE.exec(BuildArgs("titan", "commit", "-m", "automated diff commit", sourceDB));
            }

            // create temp dir .tempdata
            Path path = Paths.get(pwd + sp + ".tempdata");
            try {
                Files.createDirectory(path);
            } catch (Exception e){
                this.cleanUp(pwd + sp + ".tempdata");
                Files.createDirectory(path);
            }

            CE.exec(BuildArgs("titan", "checkout", "-c", targetState, sourceDB));
            CE.exec(BuildArgs("titan", "stop", sourceDB), false);

            // activate volume per mount
            for (TitanVolume vol : GetVolumes(repo)) {
                Volume volume = vol.GetVolume(repo.getName(), vol.getName());
                Map<String, String> config = vol.GetVolumeConfig(volume);
                vol.ActivateVolume(repo.getName(), vol.getName());
                CE.exec(BuildArgs("docker", "cp", "-a", "titan-docker-server:" + config.get("mountpoint") + sp + ".", pwd + sp + ".tempdata" + sp + vol.getName() ), false);
                vol.DeactivateVolume(repo.getName(), vol.getName());
            }

            // Build new run args from targetDB
            List<String> runArgs = BuildArgs("docker", "run", "-d");
            for (String env: GetEnv(repo)) {
                runArgs.add("-e");
                runArgs.add(env);
            }
            runArgs.add("--name");
            targetName = SOURCE_DB + "-" + CreateRandomString(8);
            runArgs.add(targetName);

            String portString = "";

            for (TitanPort port: GetPorts(repo)) {
                ServerSocket s = new ServerSocket(0);
                runArgs.add("-p");
                int p = s.getLocalPort();
                runArgs.add(p + ":" + port.getPort());
                portString = String.valueOf(p);
                s.close();
            }

            runArgs.add(GetImage(repo).getDigest());
            CE.exec(runArgs, false);

            //Wait for new container to fully initialize
            Thread.sleep(3000);

            CE.exec(BuildArgs("docker", "stop", targetName), false);
            for (TitanVolume vol: GetVolumes(repo)) {
                Volume volume = vol.GetVolume(repo.getName(), vol.getName());
                Map<String, String> config = vol.GetVolumeConfig(volume);
                CE.exec(BuildArgs("docker", "cp", "-a", pwd + sp + ".tempdata" + sp + vol.getName() + sp + ".", targetName + ":" + vol.getPath() ), false);
            }
            CE.exec(BuildArgs("docker", "start", targetName), false);

            // get URL
            String url = resultsBuilder.getCommandScope().getArgumentValue(URL_ARG);
            JDBCHelper urlHelper = JDBCHelper.fromString(url);

            // substitute PORT
            urlHelper.setPort(portString);

            // set REFERENCE_URL credentials
            resultsBuilder.getCommandScope().addArgumentValue(
                    REFERENCE_URL_ARG,
                    urlHelper.toString()
            );

            if (sourceState != null) {
                CE.exec(BuildArgs("titan", "checkout", "-c", sourceState, sourceDB));
            } else {
                String lastCommit = GetLatestCommit(repo.getName());
                CE.exec(BuildArgs("titan", "checkout", "-c", lastCommit, sourceDB));
            }
        }

        Thread.sleep(3000);

        // Execute Diff
        try {
            final CommandScope commandScope = new CommandScope("diff");
            commandScope.addArgumentValue(
                    REFERENCE_URL_ARG,
                    resultsBuilder.getCommandScope().getArgumentValue(REFERENCE_URL_ARG)
            );
            commandScope.addArgumentValue(
                    REFERENCE_USERNAME_ARG,
                    resultsBuilder.getCommandScope().getArgumentValue(USERNAME_ARG)
            );
            commandScope.addArgumentValue(
                    REFERENCE_PASSWORD_ARG,
                    resultsBuilder.getCommandScope().getArgumentValue(PASSWORD_ARG)
            );
            commandScope.setOutput(resultsBuilder.getOutputStream());
            CommandResults result = commandScope.execute();
        } catch (Exception e) {

            //TODO this can hang
            if (sourceDB != null && targetState != null) {
                CE.exec(BuildArgs("docker", "rm", targetName, "-f"));
            }

            this.cleanUp(pwd + sp + ".tempdata");
            UIService ui = Scope.getCurrentScope().getUI();
            ui.sendErrorMessage(e.getMessage());
            throw new Exception(e);
        }

        // Clean Up
        if (sourceDB != null && targetState != null) {
            CE.exec(BuildArgs("docker", "rm", targetName, "-f"));
            this.cleanUp(pwd + sp + ".tempdata");
            if (sourceState == null) {
                String lastCommit = GetLatestCommit(sourceDB);
                CE.exec(BuildArgs("titan", "delete", "-c", lastCommit, sourceDB));
            }
        }
    }

    private void cleanUp(String path) throws IOException {
        Path p = Paths.get(path);
        try (Stream<Path> walk = Files.walk(p)) {
            walk.sorted(Comparator.reverseOrder()).forEach(this::deleteFiles);
        }
    }

    private void deleteFiles(Path path) {
        try {
            Files.delete(path);
        } catch ( IOException e ) {
            UIService ui = Scope.getCurrentScope().getUI();
            ui.sendErrorMessage("Unable to delete " + path, e);
        }
    }
}

