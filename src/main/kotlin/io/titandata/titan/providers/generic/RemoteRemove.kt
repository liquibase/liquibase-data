/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.RemotesApi
import liquibase.Scope
import liquibase.ui.UIService

class RemoteRemove(
    private val remotesApi: RemotesApi = RemotesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun remove(container: String, remote: String) {
        remotesApi.deleteRemote(container, remote)
        ui?.sendMessage("Removed $remote from $container")
    }
}
