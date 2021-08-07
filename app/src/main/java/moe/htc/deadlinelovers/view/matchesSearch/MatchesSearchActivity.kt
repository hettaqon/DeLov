package moe.htc.deadlinelovers.view.matchesSearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.widget.SearchView

import com.google.gson.Gson

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.MatchesAdapter
import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.utils.invisible
import moe.htc.deadlinelovers.utils.visible
import moe.htc.deadlinelovers.view.matchesDetail.MatchesDetailActivity

import kotlinx.android.synthetic.main.activity_matches_search.*

class MatchesSearchActivity : AppCompatActivity(), MatchesSearchView {

    private lateinit var presenter: MatchesSearchPresenter

    private lateinit var events: MutableList<Matches>
    private lateinit var listAdapter: MatchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_matches_search)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search_view, menu)

        val searchMenu = menu?.findItem(R.id.mn_search_view)
        val searchView = searchMenu?.actionView as SearchView

        searchView.isIconified = false

        listenSearchView(searchView)

        return super.onCreateOptionsMenu(menu)
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

    override fun showEmptyData() {
        progress_bar.invisible()
        recycler_view.invisible()
        tv_empty.visible()
    }

    override fun showEventList(data: MutableList<Matches>) {
        events.clear()
        events.addAll(data)
        listAdapter.notifyDataSetChanged()
        recycler_view.scrollToPosition(0)
    }

    private fun setupEnv() {
        presenter = MatchesSearchPresenter(this, ApiRepository(), Gson())

        events = mutableListOf()
        listAdapter = MatchesAdapter(events) {
            MatchesDetailActivity.start(this, it)
        }

        with(recycler_view) {
            adapter = listAdapter
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        }

        presenter.getMatchesSearch()
    }

    private fun listenSearchView(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                presenter.getMatchesSearch(query.toString())

                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query.toString().isEmpty()) presenter.getMatchesSearch(query.toString())

                return true
            }
        })
    }
}
