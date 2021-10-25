package org.liquibase.ext.data;

import io.titandata.titan.Titan;
import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandDefinition;
import liquibase.command.CommandResultsBuilder;

public class CheckoutCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "data", "checkout" };
    public static final CommandArgumentDefinition<String> REPO;
    public static final CommandArgumentDefinition<String> COMMIT;
    public static final CommandArgumentDefinition<String> TAGS;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        REPO = builder.argument("repository", String.class)
                .description("name of the target repository")
                .required()
                .build();
        COMMIT = builder.argument("commit", String.class)
                .description("commit to checkout")
                .build();
        TAGS = builder.argument("tags", String.class)
                .description("tag to filter latest commit, if commit is not specified, comma separated")
                .build();
    }

    @Override
    public void adjustCommandDefinition(CommandDefinition commandDefinition) {
        commandDefinition.setShortDescription("Checkout a specific commit");
        commandDefinition.setGroupLongDescription(new String[]{"titan"}, "Titan Data Version Control");
    }

    @Override
    public String[][] defineCommandNames() {
        return new String[][]{COMMAND_NAME};
    }

    @Override
    public void run(CommandResultsBuilder resultsBuilder) throws Exception {
        //Collect Arguments
        String commit = resultsBuilder.getCommandScope().getArgumentValue(COMMIT);
        String tags = resultsBuilder.getCommandScope().getArgumentValue(TAGS);
        String repo = resultsBuilder.getCommandScope().getArgumentValue(REPO);

        // Map to Titan CLI params
        Titan titan = new Titan(repo);
        titan.getProvider().checkout(titan.getContainer(), commit, titan.toList(tags));
    }
}
