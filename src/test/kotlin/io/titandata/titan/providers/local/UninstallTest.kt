package io.titandata.titan.providers.local

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class UninstallTest {
    private fun exit(message: String, code: Int) {}
    private fun remove(container: String, force: Boolean) {}
    private val command = Uninstall("version", ::exit, ::remove)

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Uninstall::class.java))
    }
}
