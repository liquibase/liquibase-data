package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class PullCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "pull" };
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
                .description("commit GUID to pull, defaults to latest")
                .build();
        REMOTE = builder.argument("remote", String.class)
                .description("name of the remote provider, defaults to origin")
                .build();
        UPDATEONLY = builder.argument("updateOnly", Boolean.class)
                .description("update tags only, do not pull data")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("filter commits to select commit to pull. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Pull a new data state from remote");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        String commit = commandResultsBuilder.getCommandScope().getArgumentValue(COMMIT);
        String remote = commandResultsBuilder.getCommandScope().getArgumentValue(REMOTE);
        String tags = commandResultsBuilder.getCommandScope().getArgumentValue(TAGS);
        Boolean updateOnly = Boolean.TRUE.equals(commandResultsBuilder.getCommandScope().getArgumentValue(UPDATEONLY));
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().pull(titan.getContainer(), commit, remote, titan.toList(tags), updateOnly);
    }
}
