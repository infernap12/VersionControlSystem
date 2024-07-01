package svcs

import svcs.commands.*

const val programName = "SVCS"
const val hyperSkillFlag = true
val fh = FileHandler()

fun main(args: Array<String>) {
    FileHandler()

    when (parseArgs(args) ?: CommandType.HELP) {
        CommandType.HELP -> CommandType.printHelp()
        CommandType.CONFIG -> config(args.drop(1))
        CommandType.ADD -> add(args.drop(1))
        CommandType.LOG -> log()
        CommandType.COMMIT -> commit(args.drop(1))
        CommandType.CHECKOUT -> checkout(args.drop(1))
    }
}

fun checkout(args: List<String>) {
    if (args.isEmpty()) {
        println("Commit id was not passed.")
        return
    }
    val hash = args[0]

    val validCommit: Boolean = fh.getCommitLog().any { it.hash == hash }
    if (validCommit) {
        fh.checkOut(hash)
        println("Switched to commit $hash.")
    } else println("Commit does not exist.")
}

fun parseArgs(args: Array<String>): CommandType? {
    //init just first
    val first = args.first().uppercase().removePrefix("--")
    try {
        return CommandType.valueOf(first)
    } catch (e: Exception) {
        println("'$first' is not a $programName command.")
        return null
    }
}


