package services

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.api.ListBranchCommand.ListMode
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
import org.eclipse.jgit.storage.file.FileRepositoryBuilder
import org.eclipse.jgit.transport.RefSpec
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

    fun getLog(to: String = "refs/heads/master"): List<RevCommit> {
        val git = Git(repository)
        val master = git.repository.resolve(to)
        val branch = git.repository.resolve("HEAD")
        val commits = git.log().addRange(master, branch).call()

        return commits.toList()
    }

    fun listBranches(): MutableList<Ref> {
        val list = Git(repository).branchList().setListMode(ListMode.REMOTE).call()
        for (l in list) {
            println(l.name)
        }
        return list
    }

    fun removeBranch(branch: Ref) {
        val git = Git(repository)
        val refSpec = RefSpec()
            .setSource(null)
            .setDestination(branch.name)
        git.push().setRefSpecs(refSpec).setRemote("origin").call()
        println("test")
    }
}