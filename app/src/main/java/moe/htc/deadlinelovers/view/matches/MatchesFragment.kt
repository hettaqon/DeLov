package moe.htc.deadlinelovers.view.matches

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.*

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.ViewPagerAdapter
import moe.htc.deadlinelovers.utils.TypeMatches
import moe.htc.deadlinelovers.view.matchesSearch.MatchesSearchActivity

import kotlinx.android.synthetic.main.fragment_matches.*

import org.jetbrains.anko.startActivity

class MatchesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_matches, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater?.inflate(R.menu.menu_search, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.mn_search -> {
                context?.startActivity<MatchesSearchActivity>()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_latest).capitalize() to MatchesEventsFragment.newInstance(TypeMatches.LATEST),
                            getString(R.string.title_upcoming).capitalize() to MatchesEventsFragment.newInstance(TypeMatches.UPCOMING)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
