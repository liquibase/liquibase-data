package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RunCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "run" };
    public static final CommandArgumentDefinition<String> NAME_ARG;
    public static final CommandArgumentDefinition<String> ENVS;
    public static final CommandArgumentDefinition<Boolean> DISABLE_PORT_FLAG;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        NAME_ARG = builder.argument("name", String.class)
                .description("optional new name for repository")
                .build();
        ENVS = builder.argument("env", String.class)
                .description("container specific environment variables")
                .build();
        DISABLE_PORT_FLAG = builder.argument("disablePortMapping", Boolean.class)
                .description("disable default port mapping from container to localhost")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("filter latest commit by tags. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Create repository and start container.");
    }

    @Override
    public String[][] defineCommandNames() { return new String[][]{COMMAND_NAME}; }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
