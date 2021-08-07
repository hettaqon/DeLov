package moe.htc.deadlinelovers.view.matchesSearch

import com.google.gson.Gson

import moe.htc.deadlinelovers.model.MatchSearchResponse
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.network.TheSportsDbApi

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MatchesSearchPresenter(private val view: MatchesSearchView,
                             private val apiRepository: ApiRepository,
                             private val gson: Gson) {

    fun getMatchesSearch(eventName: String = "") {
        view.showLoading()

        doAsync {
            val data = gson.fromJson(apiRepository
                    .doRequest(TheSportsDbApi.getMatchesSearch(eventName)),
                    MatchSearchResponse::class.java
            )

            uiThread {
                view.hideLoading()

                try {
                    view.showEventList(data.events)
                } catch (e: Exception) {
                    view.showEmptyData()
                }
            }
        }
    }
}
