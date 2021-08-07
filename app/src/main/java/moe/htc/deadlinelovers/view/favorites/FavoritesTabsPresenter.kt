package moe.htc.deadlinelovers.view.favorites

import android.content.Context

import moe.htc.deadlinelovers.utils.database
import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.Teams

import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoritesTabsPresenter(private val context: Context?,
                             private val view: FavoritesTabsView) {

    fun getFavoritedMatches() {
        view.showLoading()

        val data: MutableList<Matches> = mutableListOf()

        context?.database?.use {
            val favorites = select(Matches.TABLE_MATCHES)
                    .parseList(classParser<Matches>())

            data.addAll(favorites)
        }

        view.hideLoading()
        view.hideSwipe()

        if (data.size > 0) {
            view.showEventList(data)
        } else {
            view.showEmptyData()
        }
    }

    fun getFavoritedTeams() {
        view.showLoading()

        val data: MutableList<Teams> = mutableListOf()

        context?.database?.use {
            val favorites = select(Teams.TABLE_TEAMS)
                    .parseList(classParser<Teams>())

            data.addAll(favorites)
        }

        view.hideLoading()
        view.hideSwipe()

        if (data.size > 0) {
            view.showTeamList(data)
        } else {
            view.showEmptyData()
        }
    }
}
