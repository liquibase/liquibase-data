package org.liquibase.ext.persistence;

import liquibase.Scope;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandResultsBuilder;
import org.liquibase.ext.persistence.utils.CommandExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TitanBase extends liquibase.command.AbstractCommandStep {

    protected final CommandExecutor CE = new CommandExecutor(Scope.getCurrentScope().getUI());

    protected List<String> BuildArgs(String... args) {
        return new ArrayList<>(Arrays.asList(args));
    }

    protected List<String> CreateTitanArg(CommandResultsBuilder builder, CommandArgumentDefinition<String> key, String titanArg) {
        List<String> r = new ArrayList<>();
        String val = builder.getCommandScope().getArgumentValue(key);
        if (val != null) {
            if (val.contains(",")) {
                // Comma delineated
                for (String s : val.split(",", 0)) {
                    r.add(titanArg);
                    r.add(s);
                }
            } else {
                r.add(titanArg);
                r.add(val);
            }
        }
        return r;
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[0][];
    }

    @Override
    public void run(CommandResultsBuilder commandResultsBuilder) throws Exception { }
}
