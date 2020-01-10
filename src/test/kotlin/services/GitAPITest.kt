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
    fun getGitBranch() {
        val branch = GitAPI.repository.branch
        assertTrue(branch == "master")
    }

    @org.junit.jupiter.api.Test
    fun getGitLog() {
        for (commit in GitAPI.getLog()) {
            println(commit.fullMessage)
            println(commit.authorIdent.name)
        }
    }
}