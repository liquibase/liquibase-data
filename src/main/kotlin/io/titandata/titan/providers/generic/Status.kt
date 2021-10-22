/*
 * Copyright (c) 2021 by Titan Project Contributors.. All rights reserved.
 */

package io.titandata.titan.providers.generic

import io.titandata.client.apis.RepositoriesApi
import io.titandata.client.apis.VolumesApi
import java.text.DecimalFormat
import kotlin.math.log10
import liquibase.Scope
import liquibase.ui.UIService

class Status(
    private val getContainersStatus: () -> List<RuntimeStatus>,
    private val repositoriesApi: RepositoriesApi = RepositoriesApi(),
    private val volumesApi: VolumesApi = VolumesApi(),
    private val ui: UIService? = Scope.getCurrentScope().ui
) {
    private val n = System.lineSeparator()

    /**
     * https://stackoverflow.com/a/5599842
     */
    private fun readableFileSize(size: Long): String {
        if (size <= 0) return "0"
        val units = arrayOf("Bi", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB")
        val digitGroups = (log10(size.toDouble()) / log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(size / Math.pow(1024.0, digitGroups.toDouble())) + " " + units[digitGroups]
    }

    fun status(container: String) {
        val status = repositoriesApi.getRepositoryStatus(container)
        for (cont in getContainersStatus()) {
            if (container == cont.name) {
                ui?.sendMessage("%20s %s$n".format("Status: ", cont.status))
            }
        }
        if (status.lastCommit != null) {
            ui?.sendMessage("%20s %s$n".format("Last Commit: ", status.lastCommit))
        }
        if (status.sourceCommit != null) {
            ui?.sendMessage("%20s %s$n".format("Source Commit: ", status.sourceCommit))
        }
        ui?.sendMessage("")

        val volumes = volumesApi.listVolumes(container)
        ui?.sendMessage("%-30s  %-12s  %s$n".format("Volume", "Uncompressed", "Compressed"))
        for (volume in volumes) {
            val volumeStatus = volumesApi.getVolumeStatus(container, volume.name)
            ui?.sendMessage(("%-30s  %-12s  %s$n".format(
                volume.properties["path"], readableFileSize(volumeStatus.logicalSize), readableFileSize(volumeStatus.actualSize))
            ))
        }
    }
}
