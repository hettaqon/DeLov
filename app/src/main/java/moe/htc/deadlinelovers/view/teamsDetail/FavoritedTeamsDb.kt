package moe.htc.deadlinelovers.view.teamsDetail

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import moe.htc.deadlinelovers.utils.database

import moe.htc.deadlinelovers.model.Teams

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class FavoritedTeamsDb {

    fun addFavorites(context: Context, data: Teams) {
        try {
            context.database.use {
                insert(Teams.TABLE_TEAMS,
                        Teams.ID_TEAM to data.teamId,
                        Teams.TEAM_BADGE to data.teamBadge,
                        Teams.TEAM to data.teamStr,
                        Teams.FORMED_YEAR to data.formedYear,
                        Teams.STADIUM to data.stadiumStr,
                        Teams.DESCRIPTION to data.descriptionEN)
            }
            context.toast("Added to favorite(s)")
        }
        catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun removeFavorites(context: Context, data: Teams) {
        try {
            context.database.use {
                delete(Teams.TABLE_TEAMS,
                        Teams.ID_TEAM + " = {id}",
                        "id" to data.teamId.toString())
            }
            context.toast("Removed from favorite(s)")
        } catch (e: SQLiteConstraintException) {
            context.toast("Error: ${e.message}")
        }
    }

    fun isFavorite(context: Context, data: Teams): Boolean {
        var bFavorite = false

        context.database.use {
            val favorites = select(Teams.TABLE_TEAMS)
                    .whereArgs(Teams.ID_TEAM + " = {id}",
                            "id" to data.teamId.toString())
                    .parseList(classParser<Teams>())

            bFavorite = !favorites.isEmpty()
        }

        return bFavorite
    }
}
