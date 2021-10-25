/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.utils

import io.titandata.titan.exceptions.CommandException
import java.io.IOException
import java.util.concurrent.TimeUnit
import liquibase.Scope
import liquibase.ui.UIService
import org.apache.commons.lang3.SystemUtils

/**
 * ORIGINAL FILE FROM TITAN-SERVER
 *
 * Handle invocation of external commands. This is a wrapper around the native interfaces that
 * handles all of the stdin/stdout errors, and will throw an exception if the command returns
 * a non-zero exit status. It is also a convenient to mock out any dependencies on the external
 * system.
 */
class CommandExecutor(
    private val timeout: Long = 10,
    private val ui: UIService? = Scope.getCurrentScope().ui
) {

    /**
     * Execute the given command. Throws an exception if the
     */
    fun exec(args: List<String>, debug: Boolean = false): String {
        val builder = ProcessBuilder()
        if (debug) ui?.sendMessage(args.stringify())
        builder.command(args)
        val process = builder.start()
        try {
            /**
             Windows has an issue with the process.waitFor being at the beginning of this loop.
             It has been moved to the end with a condition special to Windows to catch this OS
             difference. Linux and MacOS want the process.waitFor at the beginning of the loop.
             */
            if (SystemUtils.IS_OS_MAC || SystemUtils.IS_OS_LINUX) {
                process.waitFor(timeout, TimeUnit.MINUTES)
            }
            val output = process.inputStream.bufferedReader().readText()
            if (process.isAlive) {
                throw IOException("Timed out waiting for command: $args")
            }
            if (debug) ui?.sendMessage("Exit Value: ${process.exitValue()}")
            if (process.exitValue() != 0) {
                val errOutput = process.errorStream.bufferedReader().readText()
                if (debug) ui?.sendMessage(errOutput)
                throw CommandException("Command failed: ${args.stringify()}",
                        exitCode = process.exitValue(),
                        output = errOutput)
            }
            if (SystemUtils.IS_OS_WINDOWS) {
                process.waitFor(timeout, TimeUnit.MINUTES)
            }
            return output
        } finally {
            process.destroy()
        }
    }

    companion object {
        fun List<String>.stringify(): String {
            var retString = ""
            for (item in this) {
                retString += "$item "
            }
            return retString
        }
    }
}
