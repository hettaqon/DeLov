package moe.htc.deadlinelovers.model

import com.google.gson.annotations.SerializedName

data class MatchSearchResponse(

    @field:SerializedName("event")
    val events: MutableList<Matches>
)