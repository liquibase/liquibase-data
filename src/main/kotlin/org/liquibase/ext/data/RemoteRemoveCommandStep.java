package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RemoteRemoveCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "remote", "rm" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> REMOTE;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        REMOTE = builder.argument("remote", String.class)
                .description("name of the remote provider, defaults to origin")
                .required()
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Remove remote from a repository");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        String remote = commandResultsBuilder.getCommandScope().getArgumentValue(REMOTE);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().remoteRemove(titan.getContainer(), remote);
    }
}
