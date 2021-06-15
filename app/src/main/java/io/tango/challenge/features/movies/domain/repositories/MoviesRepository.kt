package io.tango.challenge.features.movies.domain.repositories

import io.tango.challenge.core.data.Resource
import io.tango.challenge.features.movies.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {
    fun fetchLatest(force: Boolean = false): Flow<Resource<Movie?>>
    fun fetchPopular(force: Boolean = false): Flow<Resource<List<Movie>?>>
    fun fetchUpcoming(force: Boolean = false): Flow<Resource<List<Movie>?>>
}
