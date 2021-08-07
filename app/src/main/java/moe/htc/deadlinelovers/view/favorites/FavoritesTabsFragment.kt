package moe.htc.deadlinelovers.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.MatchesAdapter
import moe.htc.deadlinelovers.adapter.TeamsAdapter
import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.Teams
import moe.htc.deadlinelovers.utils.TypeFavorites
import moe.htc.deadlinelovers.utils.invisible
import moe.htc.deadlinelovers.utils.visible
import moe.htc.deadlinelovers.view.matchesDetail.MatchesDetailActivity
import moe.htc.deadlinelovers.view.teamsDetail.TeamsDetailActivity

import kotlinx.android.synthetic.main.fragment_favorites_matches.*

import org.jetbrains.anko.bundleOf

class FavoritesTabsFragment : Fragment(), FavoritesTabsView {

    companion object {
        private const val TYPE_FAVORITES = "TYPE_FAVORITES"

        fun newInstance(fragmentType: TypeFavorites): FavoritesTabsFragment {
            val fragment = FavoritesTabsFragment()
            fragment.arguments = bundleOf(TYPE_FAVORITES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeFavorites

    private lateinit var presenter: FavoritesTabsPresenter

    private lateinit var events: MutableList<Matches>
    private lateinit var eventsAdapter: MatchesAdapter

    private lateinit var teams: MutableList<Teams>
    private lateinit var teamsAdapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()

        swipeRefreshFav.setOnRefreshListener {
            setupEnv()
        }
    }

    override fun onResume() {
        super.onResume()

        if (fragmentType == TypeFavorites.MATCHES) presenter.getFavoritedMatches()
        else presenter.getFavoritedTeams()
    }

    override fun showLoading() {
        progress_bar.visible()
        recycler_view.invisible()
        tv_empty.invisible()
    }

    override fun hideLoading() {
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun hideSwipe() {
        swipeRefreshFav.isRefreshing = false
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<Matches>) {
        events.clear()
        events.addAll(data)
        eventsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    override fun showTeamList(data: MutableList<Teams>) {
        teams.clear()
        teams.addAll(data)
        teamsAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_FAVORITES) as TypeFavorites
        presenter = FavoritesTabsPresenter(context, this)

        when (fragmentType) {
            TypeFavorites.MATCHES -> {
                events = mutableListOf()
                eventsAdapter = MatchesAdapter(events) {
                    MatchesDetailActivity.start(context, it)
                }
            }

            TypeFavorites.TEAMS -> {
                teams = mutableListOf()
                teamsAdapter = TeamsAdapter(teams) {
                    TeamsDetailActivity.start(context, it)
                }
            }
        }

        with(recycler_view) {
            adapter = if (fragmentType == TypeFavorites.MATCHES) eventsAdapter else teamsAdapter
            layoutManager = LinearLayoutManager(context)
            if (fragmentType == TypeFavorites.TEAMS) addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        if (fragmentType == TypeFavorites.MATCHES) presenter.getFavoritedMatches()
        else presenter.getFavoritedTeams()
    }
}
