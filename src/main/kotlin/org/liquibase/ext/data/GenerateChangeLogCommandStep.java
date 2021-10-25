package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.Scope;
import liquibase.command.*;
import liquibase.configuration.ConfigurationValueObfuscator;
import liquibase.ui.UIService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateChangeLogCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "generateChangelog" };
    public static final CommandArgumentDefinition<String> TARGET_DB;
    public static final CommandArgumentDefinition<String> TARGET_STATE;

    //Copied from liquibase-core/src/main/java/liquibase/command/core/GenerateChangelogCommandStep.java
    public static final CommandArgumentDefinition<String> USERNAME_ARG;
    public static final CommandArgumentDefinition<String> PASSWORD_ARG;
    public static final CommandArgumentDefinition<String> URL_ARG;
    public static final CommandArgumentDefinition<String> CHANGELOG_FILE_ARG;
    public static final CommandArgumentDefinition<String> DATA_OUTPUT_DIRECTORY;
    public static final CommandArgumentDefinition<String> EXCLUDE_OBJECTS_ARG;
    public static final CommandArgumentDefinition<String> INCLUDE_OBJECTS_ARG;
    public static final CommandArgumentDefinition<String> SCHEMAS_ARG;
    public static final CommandArgumentDefinition<String> DIFF_TYPES_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_ARG;
    public static final CommandArgumentDefinition<String> DRIVER_PROPERTIES_FILE_ARG;
    public static final CommandArgumentDefinition<String> OVERWRITE_OUTPUT_FILE_ARG;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        TARGET_DB = builder.argument("targetDb", String.class)
                .description("Name of Target Database")
                .required()
                .build();
        TARGET_STATE = builder.argument("targetState", String.class)
                .description("Commit for Target Database, defaults to current (optional)")
                .build();

        //Copied from liquibase-core/src/main/java/liquibase/command/core/GenerateChangelogCommandStep.java
        URL_ARG = builder.argument("url", String.class).required()
                .description("The JDBC database connection URL").build();
        USERNAME_ARG = builder.argument("username", String.class)
                .description("Username to use to connect to the database").build();
        PASSWORD_ARG = builder.argument("password", String.class)
                .description("Password to use to connect to the database")
                .setValueObfuscator(ConfigurationValueObfuscator.STANDARD)
                .build();
        DRIVER_ARG = builder.argument("driver", String.class)
                .description("The JDBC driver class").build();
        DRIVER_PROPERTIES_FILE_ARG = builder.argument("driverPropertiesFile", String.class)
                .description("The JDBC driver properties file").build();
        CHANGELOG_FILE_ARG = builder.argument("changelogFile", String.class).required()
                .description("File to write changelog to").build();
        DATA_OUTPUT_DIRECTORY = builder.argument("dataOutputDirectory", String.class)
                .description("Directory to write table data to").build();
        EXCLUDE_OBJECTS_ARG = builder.argument("excludeObjects", String.class)
                .description("Objects to exclude from diff").build();
        INCLUDE_OBJECTS_ARG = builder.argument("includeObjects", String.class)
                .description("Objects to include in diff").build();
        SCHEMAS_ARG = builder.argument("schemas", String.class)
                .description("Schemas to include in diff").build();
        DIFF_TYPES_ARG = builder.argument("diffTypes", String.class)
                .description("Types of objects to compare").build();
        OVERWRITE_OUTPUT_FILE_ARG = builder.argument("overwriteOutputFile", String.class)
                .description("Flag to allow overwriting of output changelog file").build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Generate a changelog");
        commandDefinition.setLongDescription("Writes Change Log XML to copy the current state of the database to standard out or a file");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        String targetDB = resultsBuilder.getCommandScope().getArgumentValue(TARGET_DB);
        String targetState = resultsBuilder.getCommandScope().getArgumentValue(TARGET_STATE);
        Titan titan = new Titan(targetDB);

        if (targetDB != null && targetState != null) {
            titan.getProvider().checkout(titan.getContainer(), targetState, new ArrayList<>());
            Thread.sleep(3000);
        }

        try {
            final CommandScope commandScope = new CommandScope("generateChangelog");
            commandScope.setOutput(resultsBuilder.getOutputStream());
            CommandResults result = commandScope.execute();
        } catch (Exception e) {
            UIService ui = Scope.getCurrentScope().getUI();
            ui.sendErrorMessage(e.getMessage());
            throw new Exception(e);
        }
    }
}
