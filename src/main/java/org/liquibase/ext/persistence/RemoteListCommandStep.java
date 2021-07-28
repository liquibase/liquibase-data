package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RemoteListCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "remote", "ls" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> REMOTE;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        REMOTE = builder.argument("remote", String.class)
                .description("name of the remote provider, defaults to origin")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("tag to filter latest commit, if commit is not specified. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("List remotes for a repository");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
