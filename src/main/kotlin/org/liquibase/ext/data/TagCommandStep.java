package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class TagCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "tag" };
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
        String commit = commandResultsBuilder.getCommandScope().getArgumentValue(COMMIT_ARG);
        String tags = commandResultsBuilder.getCommandScope().getArgumentValue(TAGS);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().tag(repo, commit, titan.toList(tags));
    }
}
