/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.local

import io.titandata.titan.clients.Docker
import io.titandata.titan.utils.CommandExecutor
import liquibase.Scope
import liquibase.ui.UIService

class Stop(
    private val commandExecutor: CommandExecutor = CommandExecutor(),
    private val docker: Docker = Docker(commandExecutor),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun stop(container: String) {
        docker.stop(container)
        ui?.sendMessage("$container stopped")
    }
}
