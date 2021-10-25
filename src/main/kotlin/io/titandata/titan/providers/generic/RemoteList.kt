/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.RemotesApi
import io.titandata.serialization.RemoteUtil
import liquibase.Scope
import liquibase.ui.UIService

class RemoteList(
    private val remotesApi: RemotesApi = RemotesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun list(container: String) {
        val remotes = remotesApi.listRemotes(container)
        ui?.sendMessage("%-20s %-20s".format("REMOTE", "URI"))
        for (remote in remotes) {
            val out = "%-20s %-20s".format(remote.name, RemoteUtil().toUri(remote).first)
            ui?.sendMessage(out)
        }
    }
}
