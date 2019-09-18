package services

object GitAPI {
    init {
    }

    fun getLogProcess(): Process {
        return runCommand("git log --pretty=oneline")
    }

    fun getLog(): String {
        val process = getLogProcess()
        process.onExit().get()
        if (process.exitValue() != 0) {
            throw Exception(process.errorStream.bufferedReader().readText())
        }
        return process.inputStream.bufferedReader().readText()
    }


    private fun runCommand(command: String): Process {
        val parts = command.split("\\s".toRegex())
        return ProcessBuilder(*parts.toTypedArray())
            .redirectOutput(ProcessBuilder.Redirect.PIPE)
            .redirectError(ProcessBuilder.Redirect.PIPE)
            .start()
    }
}