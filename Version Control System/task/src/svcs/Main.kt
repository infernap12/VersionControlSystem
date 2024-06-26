package svcs

const val programName = "SVCS"
const val hyperSkill = true

fun main(args: Array<String>) {

    parseArgs(args)?.let { if (it == Commands.HELP) Commands.printHelp() else println(it.helpText) }
}

fun parseArgs(args: Array<String>): Commands? {
    //init just first
    val first = args.first().uppercase().removePrefix("--")
    try {
        return Commands.valueOf(first)
    } catch (e: Exception) {
        println("'$first' is not a $programName command.")
        return null
    }
}
