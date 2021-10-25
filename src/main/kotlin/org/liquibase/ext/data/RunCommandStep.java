package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class RunCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "run" };
    public static final CommandArgumentDefinition<String> IMAGE;
    public static final CommandArgumentDefinition<String> NAME_ARG;
    public static final CommandArgumentDefinition<String> ENVS;
    public static final CommandArgumentDefinition<Boolean> DISABLE_PORT_FLAG;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        IMAGE = builder.argument("image", String.class)
                .description("the container image to run")
                .required()
                .build();
        NAME_ARG = builder.argument("name", String.class)
                .description("optional new name for repository")
                .required()
                .build();
        ENVS = builder.argument("env", String.class)
                .description("container specific environment variables")
                .build();
        DISABLE_PORT_FLAG = builder.argument("disablePortMapping", Boolean.class)
                .description("disable default port mapping from container to localhost")
                .build();

        //TODO there are no tags on Run
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
        //Collect Arguments
        String name = commandResultsBuilder.getCommandScope().getArgumentValue(NAME_ARG);
        String envs = commandResultsBuilder.getCommandScope().getArgumentValue(ENVS);
        String tags = commandResultsBuilder.getCommandScope().getArgumentValue(TAGS);
        Boolean disablePort = Boolean.TRUE.equals(commandResultsBuilder.getCommandScope().getArgumentValue(DISABLE_PORT_FLAG));
        String image = commandResultsBuilder.getCommandScope().getArgumentValue(IMAGE);

        // Map to Titan CLI params
        Titan titan = new Titan(name);
        titan.getProvider().run(image, titan.getContainer(), titan.toList(envs), titan.toList(tags), disablePort);

    }
}
