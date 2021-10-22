/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.OperationsApi
import io.titandata.client.infrastructure.ClientException
import io.titandata.models.Operation
import liquibase.Scope
import liquibase.ui.UIService

class Abort(
    private val exit: (message: String, code: Int) -> Unit,
    private val operationsApi: OperationsApi = OperationsApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    fun abort(repository: String) {
        try {
            val operations = operationsApi.listOperations(repository)
            var abortCount = 0
            for (operation in operations) {
                if (operation.state == Operation.State.RUNNING) {
                    ui?.sendMessage("aborting operation ${operation.id}")
                    operationsApi.deleteOperation(operation.id)
                    abortCount++
                }
            }
            if (abortCount == 0) {
                exit("no operation in progress", 0)
            }
        } catch (e: ClientException) {
            exit(e.message!!, 1)
        }
    }
}
