package svcs.commands

import svcs.fh

fun log(args: List<String>) {
    fh.logFile.readLines().drop(1).forEach(::println)
}