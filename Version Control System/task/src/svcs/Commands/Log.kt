package svcs.commands

import svcs.fh

fun log() {
    fh.logFile.readLines().drop(1).forEach(::println)
}