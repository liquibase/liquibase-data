package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class GenerateChangeLogCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "generateChangeLog" };
    public static final CommandArgumentDefinition<String> TARGETDB;
    public static final CommandArgumentDefinition<String> TARGETSTAGE;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        TARGETDB = builder.argument("targetDB", String.class)
                .description("Name of Target Database")
                //.required()
                .build();
        TARGETSTAGE = builder.argument("targetState", String.class)
                .description("Commit for Target Database, defaults to current (optional)")
                .build();
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
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
