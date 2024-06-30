package svcs

import svcs.commands.*

const val programName = "SVCS"
const val hyperSkillFlag = true
val fh = FileHandler()

fun main(args: Array<String>) {
    FileHandler()

    when (val cmd = parseArgs(args)) {
        CommandType.HELP -> CommandType.printHelp()
        CommandType.CONFIG -> config(args.drop(1))
        CommandType.ADD -> add(args.drop(1))
        CommandType.LOG -> log(args.drop(1))
        CommandType.COMMIT -> commit(args.drop(1))
        CommandType.CHECKOUT -> println(cmd.helpText)
        null -> TODO()
        else -> TODO()
    }
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


