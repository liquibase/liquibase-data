package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class CommitCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "commit" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> MESSAGE;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        MESSAGE = builder.argument("message", String.class)
                .description("commit message")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("tag to set")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Commit current data state");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        String message = commandResultsBuilder.getCommandScope().getArgumentValue(MESSAGE);
        String tags = commandResultsBuilder.getCommandScope().getArgumentValue(TAGS);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().commit(titan.getContainer(), message, titan.toList(tags));
    }
}
