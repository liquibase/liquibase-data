package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class DiffCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{  "diff" };
    public static final CommandArgumentDefinition<String> SOURCEDB;
    public static final CommandArgumentDefinition<String> TARGETDB;
    public static final CommandArgumentDefinition<String> SOURCESTATE;
    public static final CommandArgumentDefinition<String> TARGETSTAGE;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        SOURCEDB = builder.argument("sourceDB", String.class)
                .description("Name of Source Database")
                //.required()
                .build();
        TARGETDB = builder.argument("targetDB", String.class)
                .description("Name of Target Database")
                //.required()
                .build();
        SOURCESTATE = builder.argument("sourceState", String.class)
                .description("Commit for Source Database, defaults to current (optional)")
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
