package org.liquibase.ext.data

import io.titandata.titan.Dependencies
import io.titandata.titan.providers.ProviderFactory
import liquibase.command.AbstractCommandStep
import liquibase.command.CommandResultsBuilder

class InstallCommandStep : AbstractCommandStep() {
    override fun defineCommandNames(): Array<Array<String>> {
        return arrayOf(COMMAND_NAME)
    }

    @Throws(Exception::class)
    override fun run(crb: CommandResultsBuilder) {
        val dependencies = Dependencies(ProviderFactory())
        val provider = dependencies.providers.create("docker", "docker")
        val props = mutableMapOf<String, String>()
        provider.install(props, false)
        dependencies.providers.addProvider(provider)
    }

    companion object {
        val COMMAND_NAME = arrayOf("data", "install")
    }
}
