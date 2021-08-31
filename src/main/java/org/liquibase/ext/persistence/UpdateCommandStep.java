package org.liquibase.ext.persistence;

import liquibase.Scope;
import liquibase.command.*;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.ui.UIService;

import java.util.List;

public class UpdateCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "data", "update" };
    public static final CommandArgumentDefinition<String> TARGET_DB;
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> URL_ARG;
    public static final CommandArgumentDefinition<String> DEFAULT_SCHEMA_NAME;
    public static final CommandArgumentDefinition<String> DEFAULT_CATALOG_NAME_ARG;
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> LABELS_ARG;
    public static final CommandArgumentDefinition<String> CONTEXTS_ARG;
    public static final CommandArgumentDefinition<String> CHANGE_EXEC_LISTENER_CLASS_ARG;
    public static final CommandArgumentDefinition<String> CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_PROPERTIES_FILE_ARG;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        TARGET_DB = builder.argument("targetDb", String.class)
                .description("Name of Target Database")
                .build();
        URL_ARG = builder.argument("url", String.class).required()
                .description("The JDBC database connection URL").build();
        DEFAULT_SCHEMA_NAME = builder.argument("defaultSchemaName", String.class)
                .description("The default schema name to use for the database connection").build();
        DEFAULT_CATALOG_NAME_ARG = builder.argument("defaultCatalogName", String.class)
                .description("The default catalog name to use for the database connection").build();
        DRIVER_ARG = builder.argument("driver", String.class)
                .description("The JDBC driver class").build();
        DRIVER_PROPERTIES_FILE_ARG = builder.argument("driverPropertiesFile", String.class)
                .description("The JDBC driver properties file").build();
        USERNAME_ARG = builder.argument("username", String.class)
                .description("Username to use to connect to the database").build();
        PASSWORD_ARG = builder.argument("password", String.class)
                .description("Password to use to connect to the database")
                .setValueObfuscator(ConfigurationValueObfuscator.STANDARD)
                .build();
        CHANGELOG_FILE_ARG = builder.argument("changelogFile", String.class).required()
                .description("The root changelog").build();
        LABELS_ARG = builder.argument("labels", String.class)
                .description("Changeset labels to match").build();
        CONTEXTS_ARG = builder.argument("contexts", String.class)
                .description("Changeset contexts to match").build();
        CHANGE_EXEC_LISTENER_CLASS_ARG = builder.argument("changeExecListenerClass", String.class)
                .description("Fully-qualified class which specifies a ChangeExecListener").build();
        CHANGE_EXEC_LISTENER_PROPERTIES_FILE_ARG = builder.argument("changeExecListenerPropertiesFile", String.class)
                .description("Path to a properties file for the ChangeExecListenerClass").build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Deploy any changes in the changelog file that have not been deployed");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        String targetDB = resultsBuilder.getCommandScope().getArgumentValue(TARGET_DB);
        String commit = GetLatestCommit(targetDB);

        try {
            final CommandScope commandScope = new CommandScope("update");
            commandScope.setOutput(resultsBuilder.getOutputStream());
            CommandResults result = commandScope.execute();
        } catch (Exception e) {
            UIService ui = Scope.getCurrentScope().getUI();
            List<String> args = BuildArgs("titan", "checkout", "-c", commit, targetDB);
            CE.exec(args);
            ui.sendErrorMessage(e.getMessage());
            throw new Exception(e);
        }
    }
}
