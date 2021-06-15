package io.tango.challenge.features.movies.data.database.mappers

import io.tango.challenge.core.data.Mapper
import io.tango.challenge.features.movies.data.database.models.MovieEntity
import io.tango.challenge.features.movies.domain.models.Movie
import javax.inject.Inject

class MovieEntityMapper @Inject constructor() : Mapper<MovieEntity, Movie>() {
    override fun map(from: MovieEntity): Movie {
        return Movie(
            id = from.id,
            title = from.title,
            description = from.description,
            posterUrl = from.posterUrl,
            heroUrl = from.heroUrl,
            isLatest = from.isLatest,
            isPopular = from.isPopular,
            isUpcoming = from.isUpcoming,
            releaseDate = from.releaseDate
        )
    }

    override fun mapReverse(from: Movie): MovieEntity {
        return MovieEntity(
            id = from.id,
            title = from.title,
            description = from.description,
            posterUrl = from.posterUrl,
            heroUrl = from.heroUrl,
            isLatest = from.isLatest,
            isPopular = from.isPopular,
            isUpcoming = from.isUpcoming,
            releaseDate = from.releaseDate
        )
    }
}
