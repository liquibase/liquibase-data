package org.liquibase.ext.persistence;

import liquibase.Scope;
import liquibase.command.CommandResultsBuilder;
import org.liquibase.ext.persistence.utils.CommandExecutor;

public class TitanBase extends liquibase.command.AbstractCommandStep {

    protected final CommandExecutor CE = new CommandExecutor(Scope.getCurrentScope().getUI());

    @Override
    public String[][] defineCommandNames() {
        return new String[0][];
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception {

    }
}
