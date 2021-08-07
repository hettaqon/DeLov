package moe.htc.deadlinelovers.view.teams

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView

import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_matches_events.*

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.TeamsAdapter
import moe.htc.deadlinelovers.model.LeagueResponse
import moe.htc.deadlinelovers.model.Leagues
import moe.htc.deadlinelovers.model.Teams
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.utils.gone
import moe.htc.deadlinelovers.utils.invisible
import moe.htc.deadlinelovers.utils.loadFirstText
import moe.htc.deadlinelovers.utils.visible
import moe.htc.deadlinelovers.view.teamsDetail.TeamsDetailActivity

import kotlinx.android.synthetic.main.fragment_teams.*
import kotlinx.android.synthetic.main.fragment_teams.progress_bar
import kotlinx.android.synthetic.main.fragment_teams.recycler_view
import kotlinx.android.synthetic.main.fragment_teams.spinner
import kotlinx.android.synthetic.main.fragment_teams.spinner_container
import kotlinx.android.synthetic.main.fragment_teams.tv_empty

class TeamsFragment : Fragment(), TeamsView {

    private lateinit var presenter: TeamsPresenter

    private lateinit var league: Leagues

    private lateinit var teams: MutableList<Teams>
    private lateinit var listAdapter: TeamsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_teams, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        listenSearchView(searchView)
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
        swipeRefreshTeam.isRefreshing = false
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

                presenter.getTeamAll(league.strLeague.toString())
            }
        }
    }

    override fun showTeamList(data: MutableList<Teams>) {
        teams.clear()
        teams.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)
        }

        presenter = TeamsPresenter(this, ApiRepository(), Gson())

        spinner.loadFirstText(context!!)

        teams = mutableListOf()
        listAdapter = TeamsAdapter(teams) {
            TeamsDetailActivity.start(context, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }

        presenter.getLeagueAll()

        swipeRefreshTeam.setOnRefreshListener {
            presenter.getLeagueAll()
        }
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getTeamSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) {
                    spinner_container.visible()
                    presenter.getTeamAll(spinner.selectedItem.toString())
                } else spinner_container.gone()

                return true
            }
        })
    }
}
