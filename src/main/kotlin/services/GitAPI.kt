package services

import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.revwalk.RevWalk
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import java.io.File
import java.nio.file.Paths


object GitAPI {
    public val repository: Repository;

    init {
        val path = Paths.get("C:\\Users\\jorge\\Documents\\repos\\jetbrains-arc-plugin\\.git")
        val builder = FileRepositoryBuilder()
        repository = builder.setGitDir(File(path.toString()))
            .readEnvironment() // scan environment GIT_* variables
            .findGitDir() // scan up the file system tree
            .build()
    }

    fun getLogProcess(): Process {
        return runCommand("git log --pretty=oneline")
    }

    fun getLog(): List<RevCommit> {
        val walk = RevWalk(repository)
        walk.markStart(walk.parseCommit(GitAPI.repository.resolve("HEAD")))
        return walk.takeWhile {
            it.parentCount == 1
        }
    }


    private fun runCommand(command: String): Process {
        val parts = command.split("\\s".toRegex())
        return ProcessBuilder(*parts.toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
    }
}