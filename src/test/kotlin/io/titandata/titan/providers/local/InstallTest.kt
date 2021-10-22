package io.titandata.titan.providers.local

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class InstallTest {
    private val command = Install("0.3.0", "Titan Project Contributors.")

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Install::class.java))
    }
}
