package moe.htc.deadlinelovers.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.ViewPagerAdapter
import moe.htc.deadlinelovers.utils.TypeFavorites

import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupEnv()
    }

    private fun setupEnv() {
        with(activity as AppCompatActivity) {
            setSupportActionBar(toolbar)

            view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                    mapOf(
                            getString(R.string.title_matches).capitalize() to FavoritesTabsFragment.newInstance(TypeFavorites.MATCHES),
                            getString(R.string.title_teams).capitalize() to FavoritesTabsFragment.newInstance(TypeFavorites.TEAMS)
                    )
            )
            tab_layout.setupWithViewPager(view_pager)
        }
    }
}
