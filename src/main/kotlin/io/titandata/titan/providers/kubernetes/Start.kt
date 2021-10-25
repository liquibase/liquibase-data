/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.kubernetes

import io.titandata.client.apis.RepositoriesApi
import io.titandata.titan.clients.Kubernetes
import liquibase.Scope
import liquibase.ui.UIService

class Start(
    private val kubernetes: Kubernetes = Kubernetes(),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun start(repoName: String) {
        val repo = repositoriesApi.getRepository(repoName)
        ui?.sendMessage("Updating deployment")
        kubernetes.startStatefulSet(repoName)
        ui?.sendMessage("Waiting for deployment to be ready")
        kubernetes.waitForStatefulSet(repoName)
        if (repo.properties["disablePortMapping"] != true) {
            ui?.sendMessage("Starting port forwarding")
            kubernetes.startPortForwarding(repoName)
        }
    }
}
