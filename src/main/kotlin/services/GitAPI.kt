package services

import org.eclipse.jgit.api.Git
import org.eclipse.jgit.lib.Ref
import org.eclipse.jgit.lib.Repository
import org.eclipse.jgit.revwalk.RevCommit
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

    fun getLog(to: String = "refs/heads/master"): List<RevCommit> {
        val git = Git(repository)
        val master = git.repository.resolve(to)
        val branch = git.repository.resolve("HEAD")
        val commits = git.log().addRange(master, branch).call()

        return commits.toList()
    }

    fun listBranches(): Collection<Ref> {
        val git = Git(repository)
        val remoteRefs: Collection<Ref> = git.lsRemote()
            .setRemote("origin")
            .setTags(false)
            .setHeads(false)
            .call()
        for (ref in remoteRefs) {
            println(ref.name + " -> " + ref.objectId.name())
        }
        return remoteRefs
    }
}