package moe.htc.deadlinelovers.model

data class Leagues(val idLeague: String?, val strLeague: String?) {

    override fun toString(): String {
        return strLeague.toString()
    }
}