/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.CommitsApi
import liquibase.Scope
import liquibase.ui.UIService

class Log(
    private val commitsApi: CommitsApi = CommitsApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    private val n = System.lineSeparator()

    fun log(container: String, tags: List<String>) {
        var first = true
        for (commit in commitsApi.listCommits(container, tags)) {
            if (!first) {
                ui?.sendMessage("")
            } else {
                first = false
            }
            val metadata = commit.properties
            ui?.sendMessage("commit ${commit.id}")
            if (metadata.containsKey("author")) {
                ui?.sendMessage("Author: ${metadata["author"]}")
            }
            if (metadata.containsKey("user")) {
                ui?.sendMessage("User: ${metadata["user"]}")
            }
            if (metadata.containsKey("email")) {
                ui?.sendMessage("Email: ${metadata["email"]}")
            }
            ui?.sendMessage("Date: ${metadata["timestamp"]}")
            if (metadata.containsKey("tags")) {
                @Suppress("UNCHECKED_CAST")
                val tags = metadata.get("tags") as Map<String, String>
                var out = ""
                if (!tags.isEmpty()) {
                    out += "Tags:"
                    for ((key, value) in tags) {
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
            if (metadata["message"] != "") {
                ui?.sendMessage("${n}${metadata["message"]}")
            }
        }
    }
}
