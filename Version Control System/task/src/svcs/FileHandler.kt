package svcs

import java.io.File

const val indexHeader = "### $programName index file ###"
const val configHeader = "### $programName config file ###"
const val logHeader = "### $programName log file ###"

class FileHandler {
    fun getMap(configFile: File): MutableMap<String, String> =
        configFile.readLines()
            .drop(1)
            .filter { Regex("\\w+: \\w+").matches(it) }
            .map { it.split(": ") }
            .associate { it[0] to it[1] }
            .toMutableMap()

    fun setMap(file: File, map: Map<String, String>) {
        file.writeText(configHeader)
        map.entries.map { "\n${it.key}: ${it.value}" }.forEach(file::appendText)
    }

    fun getIndex(indexFile: File): List<File> = indexFile.readLines().drop(1).map { File(it) }

    fun writeIndex(indexFile: File, files: Set<File>) {
        indexFile.writeText(indexHeader)
        files.forEach { indexFile.appendText("\n${it.canonicalPath}") }
    }

    fun writeCommit(hash: String, indexedFiles: MutableSet<File>) {
        //create commit entry folder
        val commitFolder = File(fh.vcs, hash).also { it.mkdir() }
        indexedFiles.forEach {
            it.copyTo(File(commitFolder, it.name))
        }
    }

    fun writeLog(commits: Collection<Commit>) {
        fh.logFile.writeText(logHeader)
        commits.map(Commit::toString).forEach(logFile::appendText)
    }

    fun getCommitLog(): List<Commit> =
        fh.logFile.readLines().drop(1).chunked(3) {
            val hash = it[0].removePrefix("commit ")
            val author = it[1].removePrefix("Author: ")
            val msg = it[2]
            Commit(hash, author, msg)
        }

    private val vcs = File("vcs") // vcs folder
    val configFile = File(vcs, "config.txt")
    val indexFile = File(vcs, "index.txt")
    val logFile = File(vcs, "log.txt")

    init {
        synchronized(vcs) {
            if (!vcs.exists()) vcs.mkdirs()
            if (!configFile.exists()) {
                configFile.writeText(configHeader)
            }
            if (!indexFile.exists()) {
                indexFile.writeText(indexHeader)
            }
            if (!logFile.exists()) {
                logFile.writeText(logHeader)
            }
        }
    }
}
