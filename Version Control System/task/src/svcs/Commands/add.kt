package svcs.commands

import svcs.fh
import java.io.File

fun add(args: List<String>) {
    val files = fh.getIndex().toMutableSet()
    if (args.isEmpty()) {
        if (files.isEmpty()) {
            println(CommandType.ADD.helpText)
        } else {
            println("Tracked files:\n" + files.joinToString("\n") { it.name })
        }
    } else {
        val fileName = args[0]
        val file = File(fileName)
        if (file.exists()) {
            println("The file '$fileName' is tracked.")
            files.add(file)
        } else {
            println("Can't find '$fileName'.")
        }
    }
    fh.writeIndex(files)
}