package services

import org.junit.jupiter.api.Assertions.assertTrue

internal class GitAPITest() {
    @org.junit.jupiter.api.Test
    fun getGitBranch() {
        val branch = GitAPI.repository.branch
        assertTrue(branch == "master")
    }

    @org.junit.jupiter.api.Test
    fun getGitLog() {
        for (commit in GitAPI.getLog(to = "HEAD~3")) {
            println(commit.fullMessage)
            println(commit.authorIdent.name)
        }
    }

    @org.junit.jupiter.api.Test
    fun getGitBranches() {
        GitAPI.listBranches()
    }

    @org.junit.jupiter.api.Test
    fun removeBranch() {
//        GitAPI.removeBranch("refs/remotes/origin/branch_to_delete")
    }
}