@file:OptIn(ExperimentalStdlibApi::class)

package svcs.commands

import svcs.Commit
import svcs.fh
import svcs.programName
import java.io.File
import java.security.MessageDigest

fun commit(args: List<String>) {
    if (args.isEmpty()) {
        println("Message was not passed.")
        return
    }
    val msg = args[0]
    val indexedFiles = fh.getIndex().toMutableSet()
    if (indexedFiles.isEmpty()) {
        println("No files added to index")
        println("Use '$programName add' to include files to be tracked")
        return
    }
    val hash = hashFiles(indexedFiles)
//    println(hash)
    val commits = fh.getCommitLog().toMutableList()
    val previousHash = commits.firstOrNull()?.hash?.removePrefix("commit ")
//    println(previousHash)

    if (hash != previousHash) {
//        println("hash diff, commiting")
        val author = fh.getMap(fh.configFile)["author"] ?: "Unknown"
        commits.add(0, Commit(hash, author, msg))
        fh.writeCommit(hash, indexedFiles)
        fh.writeLog(commits)
        println("Changes are committed.")
    } else println("Nothing to commit.")

}




fun hashFiles(files: Collection<File>): String {
    // make a hash?
    val md = MessageDigest.getInstance("SHA-256")
    files.map(File::readBytes).forEach(md::update)
    return md.digest().toHexString()
}