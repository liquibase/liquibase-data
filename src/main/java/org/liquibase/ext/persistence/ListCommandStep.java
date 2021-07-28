package org.liquibase.ext.persistence;

import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class ListCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "ls" };

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

    }
}
