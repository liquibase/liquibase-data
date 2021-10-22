package io.titandata.titan.providers.local

import io.titandata.titan.clients.Docker
import io.titandata.titan.utils.CommandExecutor
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class StartTest {
    private val executor = mock<CommandExecutor> {
        on { exec(listOf("docker", "start", "container")) } doReturn ""
    }
    private val docker = Docker(executor)
    private val command = Start(executor, docker)

    @Test
    fun `can instantiate`() {
        assertThat(command, instanceOf(Start::class.java))
    }

    // TODO fix tests with liquibase UI output instead of system print
//    @Test
//    fun `can start`() {
//        val byteStream = ByteArrayOutputStream()
//        System.setOut(PrintStream(byteStream))
//        command.start("container")
//        byteStream.flush()
//        val expected = String(byteStream.toByteArray()).trim()
//        assertEquals(expected, "container started")
//    }
}
