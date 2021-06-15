package io.tango.challenge.features.movies.data.network.mappers

import io.tango.challenge.core.data.Mapper
import io.tango.challenge.features.movies.data.network.models.MovieResponse
import io.tango.challenge.features.movies.domain.models.Movie
import javax.inject.Inject

class MovieResponseMapper @Inject constructor(
    private val baseImageUrl: String
) : Mapper<MovieResponse, Movie>() {
    override fun map(from: MovieResponse): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            description = from.description,
            posterUrl = if (from.posterUrl != null) baseImageUrl + from.posterUrl else null,
            heroUrl = if (from.heroUrl != null) baseImageUrl + from.heroUrl else null,
            releaseDate = from.releaseDate
        )
    }
}
