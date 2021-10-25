package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RemoveCommandStep extends liquibase.command.AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{ "data", "rm" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<Boolean> FORCE;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        FORCE = builder.argument("force", Boolean.class)
                .description("Destroy all repositories")
                .build();
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Remove a repository");
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        //Collect Arguments
        Boolean force = Boolean.TRUE.equals(commandResultsBuilder.getCommandScope().getArgumentValue(FORCE));
        String repo = commandResultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().remove(repo, force);
    }
}
