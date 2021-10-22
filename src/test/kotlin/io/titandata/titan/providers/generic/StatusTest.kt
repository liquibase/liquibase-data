package io.titandata.titan.providers.generic

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class StatusTest {
    private fun getContainersStatus(): List<RuntimeStatus> {
        return emptyList()
    }
    private val command = Status(::getContainersStatus)

    @Test
    fun `can instantiate`() {
        assertThat(command, CoreMatchers.instanceOf(Status::class.java))
    }
}
