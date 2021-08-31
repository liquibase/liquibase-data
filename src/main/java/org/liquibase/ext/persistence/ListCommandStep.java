package org.liquibase.ext.persistence;

import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.List;

public class ListCommandStep extends TitanBase {

    public static final String[] COMMAND_NAME = new String[]{ "data", "ls" };

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("List repositories");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {
        List<String> args = BuildArgs("titan", "ls");
        CE.exec(args);
    }
}
