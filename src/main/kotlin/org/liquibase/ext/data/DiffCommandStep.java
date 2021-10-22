package org.liquibase.ext.data;

import io.titandata.models.Commit;
import io.titandata.models.RepositoryStatus;
import io.titandata.titan.Titan;
import io.titandata.titan.providers.Metadata;
import io.titandata.titan.providers.Port;
import io.titandata.titan.providers.Volume;
import io.titandata.titan.utils.JDBCHelper;
import liquibase.Scope;
import liquibase.command.*;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.ui.UIService;

import java.io.IOException;
import java.net.ServerSocket;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;

public class DiffCommandStep extends liquibase.command.AbstractCommandStep {

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

        Titan titan = new Titan(sourceDB);

        String pwd = System.getProperty("user.dir");
        String targetName = "";
        String sp = System.getProperty("file.separator");

        if (sourceDB != null && targetState != null) {
            if (sourceState == null) {
                titan.getProvider().commit(titan.getContainer(), "automated diff commit", new ArrayList<>());
            }

            // create temp dir .tempdata
            Path path = Paths.get(pwd + sp + ".tempdata");
            try {
                Files.createDirectory(path);
            } catch (Exception e){
                this.cleanUp(pwd + sp + ".tempdata");
                Files.createDirectory(path);
            }

            titan.getProvider().checkout(titan.getContainer(), targetState, new ArrayList<>());
            titan.getProvider().stop(titan.getContainer());

            RepositoryStatus repoStatus = titan.getProvider().getRepositoriesApi().getRepositoryStatus(titan.getContainer());
            Commit commit = titan.getProvider().getCommitsApi().getCommit(titan.getContainer(), repoStatus.getLastCommit());
            Metadata metadata = Metadata.Companion.load(commit.getProperties());

            // activate volume per mount
            for (Volume vol : metadata.getVolumes()) {
                io.titandata.models.Volume volume = titan.getProvider().getVolumesApi().getVolume(titan.getContainer(), vol.getName());
                titan.getProvider().getVolumesApi().activateVolume(titan.getContainer(), vol.getName());
                String mountpoint = (String) volume.getConfig().get("mountpoint");
                titan.getProvider().getDocker().cpOut(mountpoint, pwd + sp + ".tempdata" + sp + vol.getName());
                titan.getProvider().getVolumesApi().deactivateVolume(titan.getContainer(), vol.getName());
            }

            // Build new run args from targetDB
            List<String> runArgs = new ArrayList<>(Arrays.asList("docker", "run", "-d"));
            for (String env: metadata.getEnvironment()) {
                runArgs.add("-e");
                runArgs.add(env);
            }

            runArgs.add("--name");
            targetName = SOURCE_DB + "-" + CreateRandomString(8);
            runArgs.add(targetName);

            String portString = "";
            for (Port port: metadata.getPorts()){
                ServerSocket s = new ServerSocket(0);
                runArgs.add("-p");
                int p = s.getLocalPort();
                runArgs.add(p + ":" + port.getPort());
                portString = String.valueOf(p);
                s.close();
            }

            runArgs.add(metadata.getImage().getDigest());
            titan.getProvider().getCommandExecutor().exec(runArgs, false);

            //Wait for new container to fully initialize
            Thread.sleep(3000);

            titan.getProvider().getDocker().stop(targetName);
            for (Volume vol : metadata.getVolumes()) {
                io.titandata.models.Volume volume = titan.getProvider().getVolumesApi().getVolume(titan.getContainer(), vol.getName());
                titan.getProvider().getVolumesApi().activateVolume(titan.getContainer(), vol.getName());
                String mountpoint = (String) volume.getConfig().get("mountpoint");
                titan.getProvider().getDocker().localcp(pwd + sp + ".tempdata" + sp + vol.getName() + sp + ".", mountpoint);
            }
            titan.getProvider().getDocker().start(targetName);

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
                titan.getProvider().checkout(titan.getContainer(), sourceState, new ArrayList<>());
            } else {
                String lastCommit = titan.getProvider().getRepositoriesApi().getRepositoryStatus(titan.getContainer()).getLastCommit();
                titan.getProvider().checkout(titan.getContainer(), lastCommit, new ArrayList<>());
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
                titan.getProvider().getDocker().rm(targetName, true);
            }

            this.cleanUp(pwd + sp + ".tempdata");
            UIService ui = Scope.getCurrentScope().getUI();
            ui.sendErrorMessage(e.getMessage());
            throw new Exception(e);
        }

        // Clean Up
        if (sourceDB != null && targetState != null) {
            titan.getProvider().getDocker().rm(targetName, true);
            this.cleanUp(pwd + sp + ".tempdata");
            if (sourceState == null) {
                String lastCommit = titan.getProvider().getRepositoriesApi().getRepositoryStatus(titan.getContainer()).getLastCommit();
                titan.getProvider().getCommitsApi().deleteCommit(sourceDB, lastCommit);
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

    //https://www.baeldung.com/java-random-string#java8-alphanumeric
    private String CreateRandomString(int len) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(len)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

