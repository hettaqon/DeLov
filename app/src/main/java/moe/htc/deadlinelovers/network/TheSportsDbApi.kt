package moe.htc.deadlinelovers.network

import moe.htc.deadlinelovers.BuildConfig

object TheSportsDbApi {

    private const val url = "${BuildConfig.BASE_URL}${BuildConfig.TSDB_API_KEY}"

    fun getLeagueAll(): String {
        return "$url/all_leagues.php"
    }

    fun getUpcomingMatches(id: String): String {
        return "$url/eventsnextleague.php?id=$id"
    }

    fun getLatestMatches(id: String): String {
        return "$url/eventspastleague.php?id=$id"
    }

    fun getMatchesSearch(eventName: String): String {
        return "$url/searchevents.php?e=$eventName"
    }

    fun getTeamDetail(id: String): String {
        return "$url/lookupteam.php?id=$id"
    }

    fun getTeamAll(leagueName: String): String {
        return "$url/search_all_teams.php?l=$leagueName"
    }

    fun getTeamSearch(teamName: String): String {
        return "$url/searchteams.php?t=$teamName"
    }

}