package moe.htc.deadlinelovers.view.matches

import com.google.gson.Gson

import moe.htc.deadlinelovers.model.MatchResponse
import moe.htc.deadlinelovers.model.LeagueResponse
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.network.TheSportsDbApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchesEventsPresenter(private val view: MatchesEventsView,
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

    fun getUpcomingMatches(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getUpcomingMatches(id)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.hideSwipe()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }

    fun getLatestMatches(id: String) {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getLatestMatches(id)),
                    MatchResponse::class.java
            )

            uiThread {
                view.hideLoading()
                view.hideSwipe()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}
