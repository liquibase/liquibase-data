package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.Collection;
import java.util.List;

public class RemoteAddCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "remote", "add" };
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
        Collection<String> remote = CreateTitanArg(commandResultsBuilder, REMOTE, "-r");
        Collection<String> params = CreateTitanArg(commandResultsBuilder, PARAMS, "-p");
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);
        String uri = commandResultsBuilder.getCommandScope().getArgumentValue(URI);

        // Map to Titan CLI params
        List<String> args = BuildArgs("titan", "remote", "add");
        args.addAll(remote);
        args.addAll(params);
        args.add(uri);
        args.add(repo);

        CE.exec(args);
    }
}
