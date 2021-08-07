package moe.htc.deadlinelovers.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Matches(
    val id: Long?,
    @SerializedName("idEvent")
    val eventId: String? = "",
    @SerializedName("dateEvent")
    val eventDate: String? = "",
    @SerializedName("strTime")
    val timeStr: String? = "",
    @SerializedName("idHomeTeam")
    val homeTeamId: String? = "",
    @SerializedName("strHomeTeam")
    val homeTeamStr: String? = "",
    @SerializedName("intHomeScore")
    val homeScore: String? = "",
    @SerializedName("strHomeGoalDetails")
    val homeGoalScorer: String? = "",
    @SerializedName("intHomeShots")
    val homeShots: String? = "",
    @SerializedName("strHomeLineupGoalkeeper")
    val homeGK: String? = "",
    @SerializedName("strHomeLineupDefense")
    val homeDEF: String? = "",
    @SerializedName("strHomeLineupMidfield")
    val homeMID: String? = "",
    @SerializedName("strHomeLineupForward")
    val homeFWD: String? = "",
    @SerializedName("strHomeLineupSubstitutes")
    val homeSUB: String? = "",
    @SerializedName("idAwayTeam")
    val awayTeamId: String? = "",
    @SerializedName("strAwayTeam")
    val awayTeamStr: String? = "",
    @SerializedName("intAwayScore")
    val awayScore: String? = "",
    @SerializedName("strAwayGoalDetails")
    val awayScorer: String? = "",
    @SerializedName("intAwayShots")
    val awayShots: String? = "",
    @SerializedName("strAwayLineupGoalkeeper")
    val awayGK: String? = "",
    @SerializedName("strAwayLineupDefense")
    val awayDEF: String? = "",
    @SerializedName("strAwayLineupMidfield")
    val awayMID: String? = "",
    @SerializedName("strAwayLineupForward")
    val awayFWD: String? = "",
    @SerializedName("strAwayLineupSubstitutes")
    val awaySUB: String? = "") : Parcelable {

    companion object {
        const val TABLE_MATCHES = "TABLE_MATCHES"
        const val ID = "ID"
        const val MATCH_ID = "MATCH_ID"
        const val DATE = "DATE"
        const val TIME = "TIME"
        const val HOME_ID = "HOME_ID"
        const val HOME_TEAM = "HOME_TEAM"
        const val HOME_SCORE = "HOME_SCORE"
        const val HOME_SCORER = "HOME_SCORER"
        const val HOME_SHOTS = "HOME_SHOTS"
        const val HOME_GK = "HOME_GK"
        const val HOME_DEF = "HOME_DEF"
        const val HOME_MID = "HOME_MID"
        const val HOME_FWD = "HOME_FWD"
        const val HOME_SUB = "HOME_SUB"
        const val AWAY_ID = "AWAY_ID"
        const val AWAY_TEAM = "AWAY_TEAM"
        const val AWAY_SCORE = "AWAY_SCORE"
        const val AWAY_SCORER = "AWAY_SCORER"
        const val AWAY_SHOTS = "AWAY_SHOTS"
        const val AWAY_GK = "AWAY_GK"
        const val AWAY_DEF = "AWAY_DEF"
        const val AWAY_MID = "AWAY_MID"
        const val AWAY_FWD = "AWAY_FWD"
        const val AWAY_SUB = "AWAY_SUB"
    }
}