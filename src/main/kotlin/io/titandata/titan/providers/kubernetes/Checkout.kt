/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.kubernetes

import io.titandata.client.apis.CommitsApi
import io.titandata.client.apis.RepositoriesApi
import io.titandata.client.apis.VolumesApi
import io.titandata.titan.clients.Kubernetes
import liquibase.Scope
import liquibase.ui.UIService

class Checkout(
    private val kubernetes: Kubernetes = Kubernetes(),
    private val commitsApi: CommitsApi = CommitsApi(),
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val volumesApi: VolumesApi = VolumesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun checkout(repoName: String, guid: String?, tags: List<String>) {
        val sourceCommit = if (guid == null) {
            if (tags.isNotEmpty()) {
                val commits = commitsApi.listCommits(repoName, tags)
                if (commits.size == 0) {
                    throw IllegalStateException("no matching commits found")
                }
                commits.first().id
            } else {
                val status = repositoriesApi.getRepositoryStatus(repoName)
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

        val repo = repositoriesApi.getRepository(repoName)

        val status = commitsApi.getCommitStatus(repoName, sourceCommit)
        if (!status.ready) {
            ui?.sendMessage("Waiting for commit to be ready")
            while (true) {
                val commitStatus = commitsApi.getCommitStatus(repoName, sourceCommit)
                if (commitStatus.ready) {
                    break
                }
                Thread.sleep(1000L)
            }
        }

        ui?.sendMessage("Checkout $sourceCommit")
        commitsApi.checkoutCommit(repoName, sourceCommit)

        ui?.sendMessage("Stopping port forwarding")
        kubernetes.stopPortFowarding(repoName)

        ui?.sendMessage("Updating deployment")
        kubernetes.updateStatefulSetVolumes(repoName, volumesApi.listVolumes(repoName).toList())

        ui?.sendMessage("Waiting for deployment to be ready")
        kubernetes.waitForStatefulSet(repoName)

        ui?.sendMessage("Starting port forwarding")
        kubernetes.startPortForwarding(repoName)
    }
}
