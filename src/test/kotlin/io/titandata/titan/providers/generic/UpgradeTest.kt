package io.titandata.titan.providers.generic

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UpgradeTest {
    private fun start(container: String) {}
    private fun stop(container: String) {}
    private fun exit(message: String, code: Int) {}
    private fun getContainersStatus(): List<RuntimeStatus> {
        return emptyList()
    }
    private val command = Upgrade(::start, ::stop, ::exit, ::getContainersStatus)

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Upgrade::class.java))
    }
}
