package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class LogCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "log" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("filter latest commit by tags. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("List commits for a repository");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        String tags = commandResultsBuilder.getCommandScope().getArgumentValue(TAGS);
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().log(titan.getContainer(), titan.toList(tags));
    }
}
