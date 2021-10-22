package org.liquibase.ext.data;

import io.titandata.titan.Dependencies;
import io.titandata.titan.providers.Provider;
import io.titandata.titan.providers.ProviderFactory;
import liquibase.Scope;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

import java.util.Iterator;
import java.util.Map;

public class ListCommandStep extends liquibase.command.AbstractCommandStep {

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
        Dependencies dependencies = new Dependencies(new ProviderFactory());
        Map<String,Provider> providers = dependencies.getProviders().list();
        Iterator<Map.Entry<String,Provider>> iterator = providers.entrySet().iterator();
        String out = String.format("%-12s  %-20s  %s" + System.lineSeparator(), "CONTEXT", "REPOSITORY", "STATUS");
        Scope.getCurrentScope().getUI().sendMessage(out);
        while (iterator.hasNext()) {
            Map.Entry<String, Provider> provider = iterator.next();
            provider.getValue().list(provider.getKey());
        }
    }
}
