/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.kubernetes

import io.titandata.client.apis.RepositoriesApi
import io.titandata.client.apis.VolumesApi
import io.titandata.titan.clients.Kubernetes
import liquibase.Scope
import liquibase.ui.UIService

class Remove(
    private val kubernetes: Kubernetes = Kubernetes(),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val volumeApi: VolumesApi = VolumesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun remove(repo: String, force: Boolean) {
        // TODO check running  & force
        // TODO why not working kubernetes.stopPortFowarding(repo)
        kubernetes.deleteStatefulSpec(repo)
        for (volume in volumeApi.listVolumes(repo)) {
            volumeApi.deleteVolume(repo, volume.name)
        }
        repositoriesApi.deleteRepository(repo)
        ui?.sendMessage("$repo removed")
    }
}
