package io.tango.challenge.features.movies.data.network.models

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val id: Long,
    @SerializedName("original_title")
    val title: String,
    @SerializedName("overview")
    val description: String,
    @SerializedName("poster_path")
    val posterUrl: String?,
    @SerializedName("backdrop_path")
    val heroUrl: String?,
    @SerializedName("release_date")
    val releaseDate: String
)
