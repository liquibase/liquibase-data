/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.kubernetes

import io.titandata.client.apis.RepositoriesApi
import io.titandata.titan.clients.Docker
import io.titandata.titan.utils.CommandExecutor
import io.titandata.titan.utils.ProgressTracker
import liquibase.Scope
import liquibase.ui.UIService

class Uninstall(
    private val titanServerVersion: String,
    private val exit: (message: String, code: Int) -> Unit,
    private val remove: (container: String, force: Boolean) -> Unit,
    private val commandExecutor: CommandExecutor = CommandExecutor(),
    private val docker: Docker = Docker(commandExecutor),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val track: (title: String, function: () -> Any) -> Unit = ProgressTracker()::track,
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun uninstall(force: Boolean, removeImages: Boolean) {
        if (docker.titanServerIsAvailable()) {
            val repositories = repositoriesApi.listRepositories()
            for (repo in repositories) {
                if (!force) {
                    exit("repository ${repo.name} exists, remove first or use '-f'", 1)
                }
                remove(repo.name, true)
            }
        }
        if (docker.titanServerIsAvailable()) docker.rm("titan-${docker.identity}-server", true)
        track("Removing Titan Docker volume") {
            docker.removeVolume("titan-${docker.identity}-data")
        }
        if (removeImages) {
            track("Removing Titan Docker image") {
                docker.removeTitanImages(titanServerVersion)
            }
        }
        ui?.sendMessage("Uninstalled titan infrastructure")
    }
}
