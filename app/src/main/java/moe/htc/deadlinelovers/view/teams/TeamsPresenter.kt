package moe.htc.deadlinelovers.view.teams

import com.google.gson.Gson
import moe.htc.deadlinelovers.model.LeagueResponse

import moe.htc.deadlinelovers.model.TeamResponse
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.network.TheSportsDbApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TeamsPresenter(private val view: TeamsView,
                     private val apiRepository: ApiRepository,
                     private val gson: Gson) {

    fun getLeagueAll() {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLeagueAll()),
                    LeagueResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.hideSwipe()
                view.showLeagueList(data)
            }
        }
    }

    fun getTeamAll(leagueName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamAll(leagueName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.hideSwipe()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getTeamSearch(teamName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getTeamSearch(teamName)),
                    TeamResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.hideSwipe()

                try {
                    view.showTeamList(data.teams)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}
