package io.tango.challenge.features.movies.data.repositories

import io.tango.challenge.features.movies.data.database.MoviesDao
import io.tango.challenge.core.data.Mapper
import io.tango.challenge.core.data.Resource
import io.tango.challenge.core.data.networkBoundResource
import io.tango.challenge.features.movies.data.database.models.MovieEntity
import io.tango.challenge.features.movies.data.network.MoviesApi
import io.tango.challenge.features.movies.data.network.models.MovieResponse
import io.tango.challenge.features.movies.di.MovieAPIMapper
import io.tango.challenge.features.movies.di.MovieDBMapper
import io.tango.challenge.features.movies.domain.models.Movie
import io.tango.challenge.features.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CachedMoviesRepository @Inject constructor(
    private val moviesDao: MoviesDao,
    private val moviesApi: MoviesApi,
    @MovieAPIMapper
    private val movieResponseMapper: Mapper<MovieResponse, Movie>,
    @MovieDBMapper
    private val movieEntityMapper: Mapper<MovieEntity, Movie>
) : MoviesRepository {

    @ExperimentalCoroutinesApi
    override fun fetchLatest(force: Boolean): Flow<Resource<Movie?>> {
        return networkBoundResource(
            fetchFromLocal = {
                moviesDao.getLatest()?.let {
                    movieEntityMapper.map(it)
                }
            },
            shouldFetchFromRemote = {
                it == null || force
            },
            fetchFromRemote = {
                moviesApi.getLatest()
            },
            processRemoteResponse = { response ->
                movieResponseMapper.map(response)
            },
            saveRemoteData = { movie ->
                movie?.let {
                    movie.isLatest = true
                    moviesDao.saveLatest(
                        movieEntityMapper.mapReverse(movie)
                    )
                }
            },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun fetchPopular(force: Boolean): Flow<Resource<List<Movie>?>> {
        return networkBoundResource(
            fetchFromLocal = {
                moviesDao.fetchPopular().let { popularMovies ->
                    movieEntityMapper.mapList(popularMovies)
                }
            },
            shouldFetchFromRemote = {
                it == null || it.isEmpty() || force
            },
            fetchFromRemote = {
                moviesApi.fetchPopular()
            },
            processRemoteResponse = { response ->
                movieResponseMapper.mapList(response.movies)
            },
            saveRemoteData = { movies ->
                movies?.let {
                    movies.forEach {
                        it.isPopular = true
                        moviesDao.saveLatest(
                            movieEntityMapper.mapReverse(it)
                        )
                    }
                }
            },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }

    @ExperimentalCoroutinesApi
    override fun fetchUpcoming(force: Boolean): Flow<Resource<List<Movie>?>> {
        return networkBoundResource(
            fetchFromLocal = {
                moviesDao.fetchUpcoming().let { upcomingMovies ->
                    movieEntityMapper.mapList(upcomingMovies)
                }
            },
            shouldFetchFromRemote = {
                it == null || it.isEmpty() || force
            },
            fetchFromRemote = {
                moviesApi.fetchUpcoming()
            },
            processRemoteResponse = { response ->
                movieResponseMapper.mapList(response.movies)
            },
            saveRemoteData = { movies ->
                movies?.let {
                    movies.forEach {
                        it.isUpcoming = true
                        moviesDao.saveLatest(
                            movieEntityMapper.mapReverse(it)
                        )
                    }
                }
            },
            onFetchFailed = { _, _ -> }
        ).flowOn(Dispatchers.IO)
    }
}