package io.titandata.titan.providers.local

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MigrateTest {
    private fun exit(message: String, code: Int) {}
    private fun commit(container: String, message: String, tags: List<String>) {}
    private val command = Migrate(::exit, ::commit)

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Migrate::class.java))
    }
}
