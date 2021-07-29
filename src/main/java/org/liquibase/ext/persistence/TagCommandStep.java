package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.Collection;
import java.util.List;

public class TagCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "tag" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> COMMIT_ARG;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        COMMIT_ARG = builder.argument("commit", String.class)
                .description("commit to tag")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("tag to filter latest commit, if commit is not specified. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Tag objects in titan");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        Collection<String> commit = CreateTitanArg(commandResultsBuilder, COMMIT_ARG, "-c");
        Collection<String> tags = CreateTitanArg(commandResultsBuilder, TAGS, "-t");
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        List<String> args = BuildArgs("titan", "rm");
        args.addAll(commit);
        args.addAll(tags);
        args.add(repo);

        CE.exec(args);
    }
}
