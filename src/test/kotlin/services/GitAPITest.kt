package services

import org.junit.jupiter.api.Assertions.assertTrue
import kotlin.test.assertEquals

internal class GitAPITest() {

    @org.junit.jupiter.api.Test
    fun getGitLogProcess() {
        val process = GitAPI.getLogProcess()
        var log = ""
        while (process.isAlive) {
            log += process.inputStream.bufferedReader().readText()
        }
        log += process.inputStream.bufferedReader().readText()
        assertTrue(log.split("\n").size > 1)
        assertEquals(0, process.exitValue())
    }

    @org.junit.jupiter.api.Test
    fun getGitLog() {
        val log = GitAPI.getLog()
        assertTrue(log.split("\n").size > 1)
    }

}