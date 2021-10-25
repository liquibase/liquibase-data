package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RemoteAddCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "remote", "add" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> REMOTE;
    public static final CommandArgumentDefinition<String> PARAMS;
    public static final CommandArgumentDefinition<String> URI;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        URI = builder.argument("uri", String.class)
                .description("URI of the remote")
                .required()
                .build();
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
        commandDefinition.setGroupLongDescription(new String[]{"titan", "remote"}, "Add, log, ls and rm remotes");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        String remote = commandResultsBuilder.getCommandScope().getArgumentValue(REMOTE);
        String params = commandResultsBuilder.getCommandScope().getArgumentValue(PARAMS);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);
        String uri = commandResultsBuilder.getCommandScope().getArgumentValue(URI);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().remoteAdd(titan.getContainer(), uri, remote, titan.toMap(params));
    }
}
