package moe.htc.deadlinelovers.view.favorites

import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.Teams

interface FavoritesTabsView {

    fun showLoading()
    fun hideLoading()
    fun hideSwipe()
    fun showEmptyData()
    fun showEventList(data: MutableList<Matches>)
    fun showTeamList(data: MutableList<Teams>)
}
