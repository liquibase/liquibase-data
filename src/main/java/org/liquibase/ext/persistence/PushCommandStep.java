package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.Collection;
import java.util.List;

public class PushCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "data", "push" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> COMMIT;
    public static final CommandArgumentDefinition<String> REMOTE;
    public static final CommandArgumentDefinition<Boolean> UPDATEONLY;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        COMMIT = builder.argument("commit", String.class)
                .description("commit GUID to push, defaults to latest")
                .build();
        REMOTE = builder.argument("remote", String.class)
                .description("name of the remote provider, defaults to origin")
                .build();
        UPDATEONLY = builder.argument("updateOnly", Boolean.class)
                .description("update tags only, do not push data")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("filter commits to select commit to push. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Push data state to remote");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        Collection<String> commit = CreateTitanArg(commandResultsBuilder, COMMIT, "-c");
        Collection<String> remote = CreateTitanArg(commandResultsBuilder, REMOTE, "-r");
        Collection<String> tags = CreateTitanArg(commandResultsBuilder, TAGS, "-t");
        Boolean updateOnly = commandResultsBuilder.getCommandScope().getArgumentValue(UPDATEONLY);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        List<String> args = BuildArgs("titan", "push");
        args.addAll(commit);
        args.addAll(remote);
        args.addAll(tags);
        if (updateOnly != null) {
            args.add("-u");
        }
        args.add(repo);

        CE.exec(args);
    }
}
