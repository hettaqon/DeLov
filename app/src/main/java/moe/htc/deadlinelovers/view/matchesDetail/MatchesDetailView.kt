package moe.htc.deadlinelovers.view.matchesDetail

import moe.htc.deadlinelovers.model.Teams

interface MatchesDetailView {

    fun showLoading()
    fun hideLoading()
    fun showTeamDetail(dataHomeTeam: MutableList<Teams>, dataAwayTeam: MutableList<Teams>)
}
