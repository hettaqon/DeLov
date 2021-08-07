package moe.htc.deadlinelovers.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase

import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.Teams

import org.jetbrains.anko.db.*

class DbHelper(context: Context) : ManagedSQLiteOpenHelper(context, "Football.db", null, 1) {

    companion object {
        private var instance: DbHelper? = null

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (instance == null) {
                instance = DbHelper(context.applicationContext)
            }

            return instance as DbHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(Matches.TABLE_MATCHES, true,
                Matches.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Matches.MATCH_ID to TEXT,
                Matches.DATE to TEXT,
                Matches.TIME to TEXT,
                Matches.HOME_ID to TEXT,
                Matches.HOME_TEAM to TEXT,
                Matches.HOME_SCORE to TEXT,
                Matches.HOME_SCORER to TEXT,
                Matches.HOME_SHOTS to TEXT,
                Matches.HOME_GK to TEXT,
                Matches.HOME_DEF to TEXT,
                Matches.HOME_MID to TEXT,
                Matches.HOME_FWD to TEXT,
                Matches.HOME_SUB to TEXT,
                Matches.AWAY_ID to TEXT,
                Matches.AWAY_TEAM to TEXT,
                Matches.AWAY_SCORE to TEXT,
                Matches.AWAY_SCORER to TEXT,
                Matches.AWAY_SHOTS to TEXT,
                Matches.AWAY_GK to TEXT,
                Matches.AWAY_DEF to TEXT,
                Matches.AWAY_MID to TEXT,
                Matches.AWAY_FWD to TEXT,
                Matches.AWAY_SUB to TEXT)

        db.createTable(Teams.TABLE_TEAMS, true,
                Teams.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Teams.ID_TEAM to TEXT,
                Teams.TEAM_BADGE to TEXT,
                Teams.TEAM to TEXT,
                Teams.FORMED_YEAR to TEXT,
                Teams.STADIUM to TEXT,
                Teams.DESCRIPTION to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Matches.TABLE_MATCHES, true)
        db.dropTable(Teams.TABLE_TEAMS, true)
    }
}

val Context.database: DbHelper
    get() = DbHelper.getInstance(applicationContext)
