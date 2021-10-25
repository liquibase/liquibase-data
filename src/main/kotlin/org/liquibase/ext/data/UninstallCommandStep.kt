package org.liquibase.ext.data

import io.titandata.titan.Dependencies
import io.titandata.titan.providers.ProviderFactory
import liquibase.command.AbstractCommandStep
import liquibase.command.CommandArgumentDefinition
import liquibase.command.CommandBuilder
import liquibase.command.CommandResultsBuilder

class UninstallCommandStep : AbstractCommandStep() {
    override fun defineCommandNames(): Array<Array<String>> {
        return arrayOf(COMMAND_NAME)
    }

    @Throws(Exception::class)
    override fun run(crb: CommandResultsBuilder) {
        val dependencies = Dependencies(ProviderFactory())
        val providers = dependencies.providers.list()
        val providerNames = providers.keys.toList()
        for (provider in providerNames) {
            // This assumes that all providers share a common path to remove images, may not hold true in the future
            providers[provider]!!.uninstall(crb.commandScope.getArgumentValue(FORCE), provider == providerNames.last())
            dependencies.providers.removeProvider(provider)
        }
    }

    companion object {
        val COMMAND_NAME = arrayOf("data", "uninstall")
        private var builder = CommandBuilder(COMMAND_NAME)
        val FORCE: CommandArgumentDefinition<Boolean> = builder.argument<Boolean>("force", Boolean::class.java)
            .description("destroy all repositories")
            .defaultValue(false)
            .build()
    }
}
