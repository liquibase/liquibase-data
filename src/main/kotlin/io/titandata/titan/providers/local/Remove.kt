/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.local

import io.titandata.client.apis.RepositoriesApi
import io.titandata.client.apis.VolumesApi
import io.titandata.titan.clients.Docker
import io.titandata.titan.exceptions.CommandException
import io.titandata.titan.utils.CommandExecutor
import java.lang.Exception
import liquibase.Scope
import liquibase.ui.UIService

class Remove(
    private val exit: (message: String, code: Int) -> Unit,
    private val commandExecutor: CommandExecutor = CommandExecutor(),
    private val docker: Docker = Docker(commandExecutor),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val volumeApi: VolumesApi = VolumesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun remove(container: String, force: Boolean) {
        try {
            val containerInfo = docker.inspectContainer(container)
            if (containerInfo != null) {
                if (!force) {
                    if (containerInfo.getJSONObject("State").getString("Status") == "running") {
                        exit("repository $container is running, stop or use '-f' to force", 1)
                    }
                }
                ui?.sendMessage("Removing repository $container")
                if (docker.containerIsRunning(container)) {
                    docker.rm(container, force)
                } else {
                    docker.rmStopped(container)
                }
            }
        } catch (e: Exception) { }

        for (volume in volumeApi.listVolumes(container)) {
            ui?.sendMessage("Deleting volume ${volume.name}")
            volumeApi.deactivateVolume(container, volume.name)
            try {
                docker.removeVolume("$container/${volume.name}", force)
            } catch (e: CommandException) {
                /*
                 * Docker will sometimes fail to launch a container after the
                 * volume has been created. The container does not exist, but
                 * docker thinks the volume is attached to a container and does
                 * not allow it to be removed. Falling back on the VolumeApi
                 * fixes this condition.
                 */
                volumeApi.deleteVolume(container, volume.name)
            }
        }

        repositoriesApi.deleteRepository(container)
        ui?.sendMessage("$container removed")
    }
}
