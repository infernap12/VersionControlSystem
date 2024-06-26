package svcs

enum class Commands(val helpText: String) {
    HELP("Prints this help"),
    CONFIG("Get and set a username."),
    ADD("Add a file to the index."),
    LOG("Show commit logs."),
    COMMIT("Save changes."),
    CHECKOUT("Restore a file."), ;

    companion object {
        fun printHelp() {
            println("These are $programName commands:")
            entries.drop(if (hyperSkill) 1 else 0)
                .forEach {
                    println(it.name.lowercase().padEnd(11) + it.helpText)
                }
        }
    }
}