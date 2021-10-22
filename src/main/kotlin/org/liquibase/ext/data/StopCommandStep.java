package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class StopCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "stop" };
    public static final CommandArgumentDefinition<String> REPO;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Stop a container for a repository");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        Titan titan = new Titan(repo);
        titan.getProvider().stop(titan.getContainer());
    }
}
