package svcs.commands

import svcs.fh


fun config(args: List<String>) {
    val configFile = fh.configFile
    val map = fh.getMap(configFile)
    if (args.isEmpty()) {
        if (!map.containsKey("author")) {
            println("Please, tell me who you are.")
        } else println("The username is ${map["author"]}.")
    } else {
        map["author"] = args[0]
        println("The username is ${args[0]}.")
    }
    fh.setMap(configFile, map)
}
