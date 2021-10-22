package io.titandata.titan.providers.local

import io.titandata.titan.clients.Docker
import io.titandata.titan.utils.CommandExecutor
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class CheckoutTest {
    private val executor = mock<CommandExecutor> {
        on { exec(listOf("docker", "stop", "container")) } doReturn "container"
        on { exec(listOf("docker", "start", "container")) } doReturn "container"
    }
    private val docker = Docker(executor)
    private val n = System.lineSeparator()

    @Test
    fun `can instantiate`() {
        val command = Checkout()
        assertThat(command, instanceOf(Checkout::class.java))
    }

    // TODO fix tests with liquibase UI output instead of system print
//    @Test
//    fun `can checkout`() {
//        val commitsApi: CommitsApi = mock()
//        doNothing().whenever(commitsApi).checkoutCommit("container", "hash")
//        val command = Checkout(executor, docker, commitsApi)
//        val byteStream = ByteArrayOutputStream()
//        System.setOut(PrintStream(byteStream))
//        command.checkout("container", "hash", listOf())
//        byteStream.flush()
//        val expected = String(byteStream.toByteArray()).trim()
//        assertEquals(expected, "Stopping container container${n}Checkout hash${n}Starting container container${n}hash checked out")
//    }
}
