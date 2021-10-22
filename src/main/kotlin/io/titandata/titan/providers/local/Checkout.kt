/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.local

import io.titandata.client.apis.CommitsApi
import io.titandata.client.apis.RepositoriesApi
import io.titandata.titan.clients.Docker
import io.titandata.titan.utils.CommandExecutor
import liquibase.Scope
import liquibase.ui.UIService

class Checkout(
    private val commandExecutor: CommandExecutor = CommandExecutor(),
    private val docker: Docker = Docker(commandExecutor),
    private val commitsApi: CommitsApi = CommitsApi(),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun checkout(container: String, guid: String?, tags: List<String>) {
        val sourceCommit = if (guid == null) {
            if (tags.isNotEmpty()) {
                val commits = commitsApi.listCommits(container, tags)
                if (commits.size == 0) {
                    throw IllegalStateException("no matching commits found")
                }
                commits.first().id
            } else {
                val status = repositoriesApi.getRepositoryStatus(container)
                if (status.sourceCommit == null) {
                    throw IllegalStateException("no commits present, run 'titan commit' first")
                }
                status.sourceCommit!!
            }
        } else {
            if (tags.isNotEmpty()) {
                throw IllegalArgumentException("tags and commit cannot both be specified")
            }
            guid
        }

        ui?.sendMessage("Stopping container $container")
        docker.stop(container)
        ui?.sendMessage("Checkout $sourceCommit")
        commitsApi.checkoutCommit(container, sourceCommit)
        ui?.sendMessage("Starting container $container")
        docker.start(container)
        ui?.sendMessage("$sourceCommit checked out")
    }
}
