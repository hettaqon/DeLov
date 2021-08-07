package moe.htc.deadlinelovers.view.matchesDetail

import com.google.gson.Gson
import moe.htc.deadlinelovers.model.TeamResponse
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.network.TheSportsDbApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class DetailPresenter(private val view: MatchesDetailView,
                      private val apiRepository: ApiRepository,
                      private val gson: Gson) {

    fun getTeamDetail(homeTeamId: String, awayTeamId: String) {
        view.showLoading()

        doAsync {
            val dataHomeTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamDetail(homeTeamId)),
                    TeamResponse::class.java
            )

            val dataAwayTeam = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamDetail(awayTeamId)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.showTeamDetail(dataHomeTeam.teams, dataAwayTeam.teams)
            }
        }
    }
}
