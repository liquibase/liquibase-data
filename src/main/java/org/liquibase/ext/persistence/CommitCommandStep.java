package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.Collection;
import java.util.List;

public class CommitCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "commit" };
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
        Collection<String> message = CreateTitanArg(commandResultsBuilder, MESSAGE, "-m");
        Collection<String> tags = CreateTitanArg(commandResultsBuilder, TAGS, "-t");
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        List<String> args = BuildArgs("titan", "checkout");
        args.addAll(message);
        args.addAll(tags);
        args.add(repo);

        CE.exec(args);
    }
}
