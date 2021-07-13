package org.liquibase.ext.persistence;

import liquibase.command.CommandArgumentDefinition;
import liquibase.command.CommandBuilder;
import liquibase.command.CommandResultsBuilder;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CloneCommandStep extends liquibase.command.AbstractCommandStep {

    public static final String[] COMMAND_NAME = new String[]{ "titan", "clone" };

    public static final CommandArgumentDefinition<String> COMMIT_ARG;
    public static final CommandArgumentDefinition<Boolean> DISABLE_PORT_FLAG;
    public static final CommandArgumentDefinition<String> NAME_ARG;

    static {
        CommandBuilder builder = new CommandBuilder(COMMAND_NAME);
        COMMIT_ARG = builder.argument("commit", String.class).build();
        DISABLE_PORT_FLAG = builder.argument("disablePortMapping", Boolean.class).build();
        NAME_ARG = builder.argument("name", String.class).build();
    }

//-p, --parameters strings     provider specific parameters. key=value format
//-t, --tags strings           filter latest commit by tags

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

        OutputStream output = resultsBuilder.getOutputStream();
        output.write(this.exec(args).getBytes(StandardCharsets.UTF_8));
    }

    private String exec(List args) throws InterruptedException, IOException {

        ProcessBuilder builder = new ProcessBuilder(new String[0]);

        builder.command(args);
        Process process = builder.start();

        String output;
        try {
            long timeout = 10;
            if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                process.waitFor(timeout, TimeUnit.MINUTES);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ( (line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.getProperty("line.separator"));
            }
            output = stringBuilder.toString();

            if (process.isAlive()) {
                throw new IOException("Timed out waiting for command: " + args);
            }

            if (process.exitValue() != 0) {
                BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                StringBuilder errorBuilder = new StringBuilder();
                String errLine = null;
                while ( (errLine = errReader.readLine()) != null) {
                    errorBuilder.append(errLine);
                    errorBuilder.append(System.getProperty("line.separator"));
                }
                String errOutput = errorBuilder.toString();
                throw new IOException("Command failed: " + this.stringify(args) + ": " + errOutput);
            }
            if (SystemUtils.IS_OS_WINDOWS) {
                process.waitFor(timeout, TimeUnit.MINUTES);
            }
        } finally {
            process.destroy();
        }
        return output;
    }

    private String stringify(List $this$stringify) {
        String retString = "";
        String item;
        for(Iterator var4 = $this$stringify.iterator(); var4.hasNext(); retString = retString + item + ' ') {
            item = (String)var4.next();
        }
        return retString;
    }
}
