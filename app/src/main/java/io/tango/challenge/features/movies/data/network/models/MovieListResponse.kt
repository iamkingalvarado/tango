package io.tango.challenge.features.movies.data.network.models

import com.google.gson.annotations.SerializedName
import io.tango.challenge.features.movies.data.network.models.MovieResponse

data class MovieListResponse(
    @SerializedName("results")
    val movies: List<MovieResponse> = emptyList()
)
