package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class CopyCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "cp" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> DESTINATION;
    public static final CommandArgumentDefinition<String> SOURCE;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        DESTINATION = builder.argument("destination", String.class)
                .description("destination of the files inside of the container")
                .build();
        SOURCE = builder.argument("source", String.class)
                .description("source location of the files on the local machine (required)")
                .required()
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Copy data into a repository");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
