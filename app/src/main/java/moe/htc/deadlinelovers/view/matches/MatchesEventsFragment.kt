package moe.htc.deadlinelovers.view.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter

import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_favorites_matches.*

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.MatchesAdapter
import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.LeagueResponse
import moe.htc.deadlinelovers.model.Leagues
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.utils.TypeMatches
import moe.htc.deadlinelovers.utils.invisible
import moe.htc.deadlinelovers.utils.loadFirstText
import moe.htc.deadlinelovers.utils.visible
import moe.htc.deadlinelovers.view.matchesDetail.MatchesDetailActivity

import kotlinx.android.synthetic.main.fragment_matches_events.*
import kotlinx.android.synthetic.main.fragment_matches_events.progress_bar
import kotlinx.android.synthetic.main.fragment_matches_events.recycler_view
import kotlinx.android.synthetic.main.fragment_matches_events.spinner
import kotlinx.android.synthetic.main.fragment_matches_events.tv_empty
import kotlinx.android.synthetic.main.fragment_teams.*

import org.jetbrains.anko.bundleOf

class MatchesEventsFragment : Fragment(), MatchesEventsView {

    companion object {
        private const val TYPE_MATCHES = "TYPE_MATCHES"

        fun newInstance(fragmentType: TypeMatches): MatchesEventsFragment {
            val fragment = MatchesEventsFragment()
            fragment.arguments = bundleOf(TYPE_MATCHES to fragmentType)

            return fragment
        }
    }

    private lateinit var fragmentType: TypeMatches

    private lateinit var presenter: MatchesEventsPresenter

    private lateinit var league: Leagues

    private lateinit var events: MutableList<Matches>
    private lateinit var listAdapter: MatchesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
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
        swipeRefreshMatch.isRefreshing = false
        progress_bar.invisible()
        recycler_view.visible()
        tv_empty.invisible()
    }

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showLeagueList(data: LeagueResponse) {
        spinner.adapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, data.leagues)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                league = spinner.selectedItem as Leagues

                when (fragmentType) {
                    TypeMatches.LATEST -> presenter.getLatestMatches(league.idLeague.toString())
                    TypeMatches.UPCOMING -> presenter.getUpcomingMatches(league.idLeague.toString())
                }
            }
        }
    }

    override fun showEventList(data: MutableList<Matches>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        fragmentType = arguments?.get(TYPE_MATCHES) as TypeMatches
        presenter = MatchesEventsPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            MatchesDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
        }

        presenter.getLeagueAll()

        swipeRefreshMatch.setOnRefreshListener {
            presenter.getLeagueAll()
        }
    }
}
