package svcs

import java.io.File

const val indexHeader = "### $programName index file ###"
const val configHeader = "### $programName config file ###"

class FileHandler {
    fun getMap(configFile: File): MutableMap<String, String> =
        configFile.readLines()
            .drop(1)
            .filter { Regex("\\w+: \\w+").matches(it) }
            .map { it.split(": ") }
            .associate { it[0] to it[1] }
            .toMutableMap()

    fun setMap(file: File, map: Map<String, String>) {
        file.writeText("### $programName config file ###")
        map.entries.map { "\n${it.key}: ${it.value}" }.forEach(file::appendText)
    }

    fun getIndex(indexFile: File): List<File> = indexFile.readLines().drop(1).map { File(it) }

    fun writeIndex(indexFile: File, files: Set<File>) {
        indexFile.writeText(indexHeader)
        files.forEach { indexFile.appendText("\n${it.canonicalPath}") }
    }

    val vcs = File("vcs") // vcs folder
    val configFile = File(vcs, "config.txt")
    val indexFile = File(vcs, "index.txt")


    init {
        synchronized(vcs) {
            if (!vcs.exists()) vcs.mkdirs()
            if (!configFile.exists()) {
                configFile.writeText("### $programName config file ###")
            }
            if (!indexFile.exists()) {
                indexFile.writeText("### $programName index file ###")
            }
        }
    }
}
