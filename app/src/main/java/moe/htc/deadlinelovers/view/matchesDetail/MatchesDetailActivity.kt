package moe.htc.deadlinelovers.view.matchesDetail

import android.content.Context
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView

import com.google.gson.Gson

import moe.htc.deadlinelovers.R
import moe.htc.deadlinelovers.R.id.mn_favorites
import moe.htc.deadlinelovers.model.Matches
import moe.htc.deadlinelovers.model.Teams
import moe.htc.deadlinelovers.network.ApiRepository
import moe.htc.deadlinelovers.utils.*

import com.squareup.picasso.Picasso

import org.jetbrains.anko.*

class MatchesDetailActivity : AppCompatActivity(), MatchesDetailView {

    companion object {
        private const val EXTRA_PARAM = "EXTRA_PARAM"

        private const val ID_HOME_BADGE = 0
        private const val ID_AWAY_BADGE = 1

        fun start(context: Context?, event: Matches) {
            context?.startActivity<MatchesDetailActivity>(EXTRA_PARAM to event)
        }
    }

    private lateinit var presenter: DetailPresenter

    private lateinit var progressBar: ProgressBar
    private lateinit var scrollView: ScrollView

    private lateinit var event: Matches

    private var menuFavorites: Menu? = null
    private var isFavorite: Boolean = false

    private val FavoritedMatchesDb = FavoritedMatchesDb()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        event = intent.getParcelableExtra(EXTRA_PARAM)

        setupLayout(event)
        setupEnv(event)
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

            mn_favorites -> {
                if (isFavorite) {
                    FavoritedMatchesDb.removeFavorites(ctx, event)
                } else {
                    FavoritedMatchesDb.addFavorites(ctx, event)
                }

                isFavorite = !isFavorite
                setFavorite()

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showLoading() {
        progressBar.visible()
        scrollView.invisible()
    }

    override fun hideLoading() {
        progressBar.invisible()
        scrollView.visible()
    }

    override fun showTeamDetail(dataHomeTeam: MutableList<Teams>, dataAwayTeam: MutableList<Teams>) {
        val imgHomeBadge = find<ImageView>(ID_HOME_BADGE)
        val imgAwayBadge = find<ImageView>(ID_AWAY_BADGE)

        Picasso.get()
                .load(dataHomeTeam[0].teamBadge)
                .placeholder(R.drawable.ic_football)
                .error(R.drawable.ic_empty)
                .into(imgHomeBadge)

        Picasso.get()
                .load(dataAwayTeam[0].teamBadge)
                .placeholder(R.drawable.ic_football)
                .error(R.drawable.ic_empty)
                .into(imgAwayBadge)
    }

    private fun setupLayout(data: Matches) {
        relativeLayout {
            scrollView = scrollView {
                linearLayout {
                    orientation = LinearLayout.VERTICAL
                    padding = dip(16)

                    textCenter(DateTime.getDateFormat(data.eventDate))
                    textCenter(DateTime.getTimeFormat(data.timeStr))

                    linearLayout {
                        gravity = Gravity.CENTER

                        textTitle(data.homeScore ?: "-")
                        textTitle(context.getString(R.string.title_vs))
                        textTitle(data.awayScore ?: "-")
                    }

                    linearLayout {
                        layoutTeamBadge(ID_HOME_BADGE, data.homeTeamStr)
                                .lparams(matchParent, wrapContent, 1f)

                        layoutTeamBadge(ID_AWAY_BADGE, data.awayTeamStr)
                                .lparams(matchParent, wrapContent, 1f)
                    }

                    line()

                    layoutDetailItem("Goals", data.homeGoalScorer, data.awayScorer)
                    layoutDetailItem("Shots", data.homeShots, data.awayShots)

                    line()

                    textSubTitle("Lineups")

                    layoutDetailItem("Goal Keeper", data.homeGK, data.awayGK)
                    layoutDetailItem("Defense", data.homeDEF, data.awayDEF)
                    layoutDetailItem("Midfield", data.homeMID, data.awayMID)
                    layoutDetailItem("Forward", data.homeFWD, data.awayFWD)
                    layoutDetailItem("Substitutes", data.homeSUB, data.awaySUB)
                }
            }

            progressBar = progressBar().lparams {
                centerInParent()
            }
        }
    }

    private fun setupEnv(data: Matches) {
        presenter = DetailPresenter(this, ApiRepository(), Gson())
        isFavorite = FavoritedMatchesDb.isFavorite(ctx, event)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Match Detail"

        presenter.getTeamDetail(data.homeTeamId.toString(), data.awayTeamId.toString())
    }

    private fun setFavorite() {
        if (isFavorite) {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_full)
        } else {
            menuFavorites?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_star_border)
        }
    }
}
