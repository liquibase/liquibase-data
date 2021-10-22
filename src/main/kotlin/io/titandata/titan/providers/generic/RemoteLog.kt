/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.RemotesApi
import io.titandata.client.infrastructure.ClientException
import io.titandata.models.Remote
import io.titandata.serialization.RemoteUtil
import liquibase.Scope
import liquibase.ui.UIService

class RemoteLog(
    private val exit: (message: String, code: Int) -> Unit,
    private val remotesApi: RemotesApi = RemotesApi(),
    private val remoteUtil: RemoteUtil = RemoteUtil(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    private val n = System.lineSeparator()

    private fun getRemotes(container: String, remoteName: String?): Array<Remote> {
        return try {
            when (remoteName.isNullOrBlank()) {
                true -> remotesApi.listRemotes(container)
                else -> arrayOf(remotesApi.getRemote(container, remoteName))
            }
        } catch (e: ClientException) {
            arrayOf(Remote("nop", "nop"))
        }
    }

    fun remoteLog(container: String, remoteName: String?, tags: List<String>) {
        val remotes = getRemotes(container, remoteName)
        if (remotes.isEmpty()) {
            exit("remote is not set, run 'remote add' first", 1)
        }

        var first = true
        loop@ for (remote in remotes) {
            if (remote.provider == "nop") {
                break@loop
            }

            try {
                val commits = remotesApi.listRemoteCommits(container, remote.name, remoteUtil.getParameters(remote), tags)
                for (commit in commits) {
                    if (!first) {
                        ui?.sendMessage("")
                    } else {
                        first = false
                    }

                    ui?.sendMessage("Commit ${commit.id}")
                    if (commit.properties.containsKey("author")) {
                        ui?.sendMessage("Author: ${commit.properties["author"]}")
                    }
                    if (commit.properties.containsKey("user")) {
                        ui?.sendMessage("User: ${commit.properties["user"]}")
                    }
                    if (commit.properties.containsKey("email")) {
                        ui?.sendMessage("Email: ${commit.properties["email"]}")
                    }
                    ui?.sendMessage("Date:   ${commit.properties["timestamp"]}")
                    if (commit.properties.containsKey("tags")) {
                        @Suppress("UNCHECKED_CAST")
                        val tagInfo = commit.properties.get("tags") as Map<String, String>
                        var out = ""
                        if (!tagInfo.isEmpty()) {
                            out += "Tags:"
                            for ((key, value) in tagInfo) {
                                out += " "
                                if (value != "") {
                                    out += "$key=$value"
                                } else {
                                    out += key
                                }
                            }
                            ui?.sendMessage(out)
                        }
                    }
                    if (commit.properties["message"] != "") {
                        ui?.sendMessage("${n}${commit.properties["message"]}")
                    }
                }
            } catch (e: ClientException) {
                ui?.sendMessage("${remote.name} has not been initialized.")
            }
        }
    }
}
