package moe.htc.deadlinelovers.view.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.view.favorites.FavoritesFragment
import moe.htc.deadlinelovers.view.matches.MatchesFragment
import moe.htc.deadlinelovers.view.teams.TeamsFragment

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupEnv()
    }

    private fun setupEnv() {
        setFragment(MatchesFragment())
        listenBottomNavigationView()
    }

    private fun listenBottomNavigationView() {
        bottom_navigation_view.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                bottom_navigation_view.selectedItemId -> return@setOnNavigationItemSelectedListener false

                R.id.bnv_matches -> {
                    setFragment(MatchesFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_teams -> {
                    setFragment(TeamsFragment())
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.bnv_favorites -> {
                    setFragment(FavoritesFragment())
                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }

    private fun setFragment(fragment: Fragment) {
        with(supportFragmentManager.beginTransaction()) {
            setTransition(androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            replace(R.id.frame_layout, fragment)
            commit()
        }
    }
}
