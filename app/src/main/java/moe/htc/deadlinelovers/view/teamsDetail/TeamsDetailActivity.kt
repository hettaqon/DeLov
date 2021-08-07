package moe.htc.deadlinelovers.view.teamsDetail

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.adapter.ViewPagerAdapter
import moe.htc.deadlinelovers.model.Teams

import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_teams_detail.*

import org.jetbrains.anko.ctx
import org.jetbrains.anko.startActivity

class TeamsDetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        fun start(context: Context?, team: Teams) {
            context?.startActivity<TeamsDetailActivity>(EXTRA_PARAM to team)
        }
    }

    private lateinit var team: Teams

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private val favoritedTeamsDb = FavoritedTeamsDb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teams_detail)

        setupEnv()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorites, menu)
        menuFavorites = menu
        setFavorite()

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()

                true
            }

            R.id.mn_favorites -> {
                if (isFavorite) {
                    favoritedTeamsDb.removeFavorites(ctx, team)
                } else {
                    favoritedTeamsDb.addFavorites(ctx, team)
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupEnv() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        loadData()

        isFavorite = favoritedTeamsDb.isFavorite(ctx, team)
        view_pager.adapter = ViewPagerAdapter(supportFragmentManager,
                mapOf(
                        getString(R.string.title_overview) to TeamsOverviewFragment.newInstance(team.descriptionEN.toString())
                )
        )
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun loadData() {
        team = intent.getParcelableExtra(EXTRA_PARAM)

        Picasso.get()
                .load(team.teamBadge)
                .placeholder(R.drawable.ic_football)
                .error(R.drawable.ic_empty)
                .into(iv_team)

        tv_name.text = team.teamStr
        tv_year.text = team.formedYear
        tv_stadium.text = team.stadiumStr
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_full)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
    }
}
