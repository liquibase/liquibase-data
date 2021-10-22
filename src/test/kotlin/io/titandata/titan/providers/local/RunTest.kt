package io.titandata.titan.providers.local

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class RunTest {
    private fun exit(message: String, code: Int) {}
    private val command = Run(::exit)

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Run::class.java))
    }
}
