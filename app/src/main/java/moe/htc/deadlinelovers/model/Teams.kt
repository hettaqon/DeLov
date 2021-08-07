package moe.htc.deadlinelovers.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Teams(
    val id: Long?,
    @SerializedName("idTeam")
    val teamId: String? = "",
    @SerializedName("strTeamBadge")
    val teamBadge: String? = "",
    @SerializedName("strTeam")
    val teamStr: String? = "",
    @SerializedName("intFormedYear")
    val formedYear: String? = "",
    @SerializedName("strStadium")
    val stadiumStr: String? = "",
    @SerializedName("strDescriptionEN")
    val descriptionEN: String? = "") : Parcelable {

    companion object {
        const val TABLE_TEAMS = "TABLE_TEAMS"
        const val ID = "ID"
        const val ID_TEAM = "ID_TEAM"
        const val TEAM_BADGE = "TEAM_BADGE"
        const val TEAM = "TEAM"
        const val FORMED_YEAR = "FORMED_YEAR"
        const val STADIUM = "STADIUM"
        const val DESCRIPTION = "DESCRIPTION"
    }
}