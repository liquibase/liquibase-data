package io.titandata.titan.providers.generic

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LogTest {

    private val n = System.lineSeparator()

    @Test
    fun `can instantiate`() {
        val command = Log()
        assertThat(command, CoreMatchers.instanceOf(Log::class.java))
    }

    // TODO fix tests with liquibase UI output instead of system print
//    @Test
//    fun `can get log`() {
//        val commitsApi: CommitsApi = mock()
//        val commitObj = io.titandata.models.Commit("uuid", mapOf("message" to "message", "author" to "unknown", "container" to "container", "runtime" to "runtime", "timestamp" to "timestamp"))
//        val commitObj2 = io.titandata.models.Commit("uuid2", mapOf("message" to "", "author" to "unknown", "container" to "container", "runtime" to "runtime", "timestamp" to "timestamp"))
//        val commitArray = arrayOf(commitObj, commitObj2)
//        doReturn(commitArray).whenever(commitsApi).listCommits("container", listOf())
//        val command = Log(commitsApi)
//
//        val byteStream = ByteArrayOutputStream()
//        System.setOut(PrintStream(byteStream))
//        command.log("container", listOf())
//        byteStream.flush()
//        val expected = String(byteStream.toByteArray()).trim()
//        assertEquals(expected, "commit uuid${n}Author: unknown${n}Date: timestamp${n}${n}message${n}${n}commit uuid2${n}Author: unknown${n}Date: timestamp")
//    }
}
