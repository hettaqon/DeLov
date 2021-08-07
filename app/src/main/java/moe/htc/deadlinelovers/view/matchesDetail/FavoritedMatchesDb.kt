package moe.htc.deadlinelovers.view.matchesDetail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import moe.htc.deadlinelovers.utils.database

import moe.htc.deadlinelovers.model.Matches

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoritedMatchesDb {

    fun addFavorites(context: Context, data: Matches) {
        try {
            context.database.use {
                insert(Matches.TABLE_MATCHES,
                        Matches.MATCH_ID to data.eventId,
                        Matches.DATE to data.eventDate,
                        Matches.TIME to data.timeStr,
                        Matches.HOME_ID to data.homeTeamId,
                        Matches.HOME_TEAM to data.homeTeamStr,
                        Matches.HOME_SCORE to data.homeScore,
                        Matches.HOME_SCORER to data.homeGoalScorer,
                        Matches.HOME_SHOTS to data.homeShots,
                        Matches.HOME_GK to data.homeGK,
                        Matches.HOME_DEF to data.homeDEF,
                        Matches.HOME_MID to data.homeMID,
                        Matches.HOME_FWD to data.homeFWD,
                        Matches.HOME_SUB to data.homeSUB,
                        Matches.AWAY_ID to data.awayTeamId,
                        Matches.AWAY_TEAM to data.awayTeamStr,
                        Matches.AWAY_SCORE to data.awayScore,
                        Matches.AWAY_SCORER to data.awayScorer,
                        Matches.AWAY_SHOTS to data.awayShots,
                        Matches.AWAY_GK to data.awayGK,
                        Matches.AWAY_DEF to data.awayDEF,
                        Matches.AWAY_MID to data.awayMID,
                        Matches.AWAY_FWD to data.awayFWD,
                        Matches.AWAY_SUB to data.awaySUB)
            }
            context.toast("Added to favorite(s)")
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorites(context: Context, data: Matches) {
        try {
            context.database.use {
                delete(Matches.TABLE_MATCHES,
                        Matches.MATCH_ID + " = {id}",
                        "id" to data.eventId.toString())
            }
            context.toast("Removed from favorite(s)")
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: Matches): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(Matches.TABLE_MATCHES)
                    .whereArgs(Matches.MATCH_ID + " = {id}",
                            "id" to data.eventId.toString())
                    .parseList(classParser<Matches>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}
