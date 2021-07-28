package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RemoteAddCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "remote", "add" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> REMOTE;
    public static final CommandArgumentDefinition<String> PARAMS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        REMOTE = builder.argument("remote", String.class)
                .description("name of the remote provider, defaults to origin")
                .build();
        PARAMS = builder.argument("parameters", String.class)
                .description("provider specific parameters. key=value format. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Set remote destination for a repository");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
