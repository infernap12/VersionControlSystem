package svcs

data class Commit(val hash: String, val author: String, val msg: String) {
    override fun toString(): String {
        return "\n" + """
                commit $hash
                Author: $author
                $msg
            """.trimIndent()
    }
}