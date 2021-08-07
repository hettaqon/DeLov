package moe.htc.deadlinelovers.view.teams

import moe.htc.deadlinelovers.model.LeagueResponse
import moe.htc.deadlinelovers.model.Teams

interface TeamsView {

    fun showLoading()
    fun hideLoading()
    fun hideSwipe()
    fun showEmptyData()
    fun showLeagueList(data: LeagueResponse)
    fun showTeamList(data: MutableList<Teams>)
}
