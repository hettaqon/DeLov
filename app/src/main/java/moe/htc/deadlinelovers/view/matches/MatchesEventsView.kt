package moe.htc.deadlinelovers.view.matches

import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.LeagueResponse

interface MatchesEventsView {

    fun showLoading()
    fun hideLoading()
    fun hideSwipe()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showEventList(data: MutableList<Matches>)
}
