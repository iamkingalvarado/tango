package io.tango.challenge.features.movies.data.network

import io.tango.challenge.features.movies.data.network.models.MovieListResponse
import io.tango.challenge.features.movies.data.network.models.MovieResponse
import retrofit2.http.GET

interface MoviesApi {
    @GET("/3/movie/latest")
    suspend fun getLatest(): MovieResponse

    @GET("/3/movie/popular")
    suspend fun fetchPopular(): MovieListResponse

    @GET("/3/movie/upcoming")
    suspend fun fetchUpcoming(): MovieListResponse
}
