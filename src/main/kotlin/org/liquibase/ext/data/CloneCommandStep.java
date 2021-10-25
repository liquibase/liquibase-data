package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.*;

public class CloneCommandStep extends liquibase.command.AbstractCommandStep {
    public static final String[] COMMAND_NAME = new String[]{ "data", "clone" };
    public static final CommandArgumentDefinition<String> URI;
    public static final CommandArgumentDefinition<String> COMMIT_ARG;
    public static final CommandArgumentDefinition<Boolean> DISABLE_PORT_FLAG;
    public static final CommandArgumentDefinition<String> NAME_ARG;
    public static final CommandArgumentDefinition<String> PARAMETERS;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        URI = builder.argument("uri", String.class)
                .description("URI of the remote to clone from")
                .required()
                .build();
        COMMIT_ARG = builder.argument("commit", String.class)
                .description("commit to checkout")
                .build();
        DISABLE_PORT_FLAG = builder.argument("disablePortMapping", Boolean.class)
                .description("disable default port mapping from container to localhost")
                .build();
        NAME_ARG = builder.argument("name", String.class)
                .description("optional new name for repository")
                .build();
        PARAMETERS = builder.argument("parameters", String.class)
                .description("provider specific parameters. key=value format. comma separated")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("filter latest commit by tags. comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Clone a remote repository to local repository");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        //Collect Arguments
        String commit = resultsBuilder.getCommandScope().getArgumentValue(COMMIT_ARG);
        String name = resultsBuilder.getCommandScope().getArgumentValue(NAME_ARG);
        String params = resultsBuilder.getCommandScope().getArgumentValue(PARAMETERS);
        String tags = resultsBuilder.getCommandScope().getArgumentValue(TAGS);
        Boolean disablePort = Boolean.TRUE.equals(resultsBuilder.getCommandScope().getArgumentValue(DISABLE_PORT_FLAG));
        String uri = resultsBuilder.getCommandScope().getArgumentValue(URI);

        // Map to Titan CLI params
        Titan titan = new Titan(name);
        titan.getProvider().clone(uri, titan.getContainer(), commit, titan.toMap(params), new ArrayList<>(), disablePort, titan.toList(tags));
    }
}
