package org.liquibase.ext.persistence;

import liquibase.command.CommandResultsBuilder;

public class RunCommandStep extends liquibase.command.AbstractCommandStep {
    @Override
    public String[][] defineCommandNames() {
        return new String[0][];
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
