package moe.htc.deadlinelovers.view.matchesSearch

import moe.htc.deadlinelovers.model.Matches

interface MatchesSearchView {

    fun showLoading()
    fun hideLoading()
    fun showEmptyData()
    fun showEventList(data: MutableList<Matches>)
}
