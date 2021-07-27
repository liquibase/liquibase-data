package org.liquibase.ext.persistence.utils;

import liquibase.ui.UIService;
import org.apache.commons.lang3.SystemUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CommandExecutor {

    private final UIService ui;
    private final Long duration;
    private final TimeUnit unit;

    public CommandExecutor(UIService ui, Long duration, TimeUnit unit) {
        this.ui = ui;
        this.duration = duration;
        this.unit = unit;
    }

    public CommandExecutor(UIService ui) {
        this.ui = ui;
        this.duration = 10L;
        this.unit = TimeUnit.MINUTES;
    }

    public void exec(List args) throws InterruptedException, IOException {

        ProcessBuilder builder = new ProcessBuilder();

        builder.command(args);
        Process process = builder.start();

        this.ui.sendMessage("----------------------------------------------------------------------");

        try {
            if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                process.waitFor(this.duration, this.unit);
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ( (line = reader.readLine()) != null) {
                this.ui.sendMessage(line);
                this.ui.sendMessage(System.getProperty("line.separator"));
            }

            if (process.isAlive()) {
                throw new IOException("Timed out waiting for command: " + args);
            }

            if (process.exitValue() != 0) {
                BufferedReader errReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
                String errLine;
                while ( (errLine = errReader.readLine()) != null) {
                    this.ui.sendErrorMessage(errLine);
                    this.ui.sendErrorMessage(System.getProperty("line.separator"));
                }
                throw new IOException("Command failed: " + this.stringify(args) + ": " + "");
            }
            if (SystemUtils.IS_OS_WINDOWS) {
                process.waitFor(this.duration, this.unit);
            }
        } finally {
            process.destroy();
        }
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
