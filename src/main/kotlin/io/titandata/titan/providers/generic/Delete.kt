/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.CommitsApi
import liquibase.Scope
import liquibase.ui.UIService

class Delete(
    private val commitsApi: CommitsApi = CommitsApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun deleteCommit(repository: String, commit: String) {
        commitsApi.deleteCommit(repository, commit)
        ui?.sendMessage("$commit deleted")
    }

    fun deleteTags(repository: String, commit: String, tags: List<String>) {
        val commit = commitsApi.getCommit(repository, commit)
        val commitTags = (commit.properties.get("tags") as Map<String, String>).toMutableMap()

        for (t in tags) {
            if (t.contains("=")) {
                val key = t.substringBefore("=")
                val value = t.substringAfter("=")
                if (commitTags.containsKey(key) && commitTags[key] == value) {
                    commitTags.remove(key)
                }
            } else {
                commitTags.remove(t)
            }
        }

        val metadata = commit.properties.toMutableMap()
        metadata["tags"] = commitTags

        commitsApi.updateCommit(repository, io.titandata.models.Commit(id = commit.id, properties = metadata))
    }
}
