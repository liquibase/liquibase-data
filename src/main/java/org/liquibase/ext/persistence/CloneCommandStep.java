package org.liquibase.ext.persistence;

import liquibase.Scope;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandResultsBuilder;
import org.liquibase.ext.persistence.utils.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class CloneCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "clone" };
    public static final CommandArgumentDefinition<String> COMMIT_ARG;
    public static final CommandArgumentDefinition<Boolean> DISABLE_PORT_FLAG;
    public static final CommandArgumentDefinition<String> NAME_ARG;
    public static final CommandArgumentDefinition<String> PARAMETERS;
    public static final CommandArgumentDefinition<String> TAGS;

    private final CommandExecutor CE = new CommandExecutor(Scope.getCurrentScope().getUI());

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        COMMIT_ARG = builder.argument("commit", String.class).build();
        DISABLE_PORT_FLAG = builder.argument("disablePortMapping", Boolean.class).build();
        NAME_ARG = builder.argument("name", String.class).build();
        PARAMETERS = builder.argument("parameters", String.class).build();
        TAGS = builder.argument("tags", String.class).build();

        //-p, --parameters strings     provider specific parameters. key=value format
        //-t, --tags strings           filter latest commit by tags
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        //System.out.print("Titan says Hello " + commandResultsBuilder.getCommandScope().getArgumentValue(CONTEXTS_ARG));
        List args = new ArrayList();
        args.add("titan");
        args.add("--version");
        CE.exec(args);
    }


}
