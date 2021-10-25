/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.kubernetes

import io.titandata.client.apis.RepositoriesApi
import io.titandata.titan.clients.Kubernetes
import liquibase.Scope
import liquibase.ui.UIService

class Stop(
    private val kubernetes: Kubernetes = Kubernetes(),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun stop(repoName: String) {
        val repo = repositoriesApi.getRepository(repoName)
        if (repo.properties["disablePortMapping"] != true) {
            ui?.sendMessage("Stopping port forwarding")
            kubernetes.stopPortFowarding(repoName)
        }
        ui?.sendMessage("Updating deployment")
        kubernetes.stopStatefulSet(repoName)
        ui?.sendMessage("Waiting for deployment to stop")
        kubernetes.waitForStatefulSet(repoName)
        ui?.sendMessage("Stopped $repoName")
    }
}
