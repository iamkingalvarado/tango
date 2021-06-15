package io.tango.challenge.features.movies.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.tango.challenge.core.data.Resource
import io.tango.challenge.features.movies.domain.managers.MovieResourceManager
import io.tango.challenge.features.movies.domain.models.Movie
import io.tango.challenge.features.movies.domain.models.Section
import io.tango.challenge.features.movies.domain.repositories.MoviesRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val movieResourceManager: MovieResourceManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel(), CoroutineScope {

    private val _loadingState = MutableLiveData<Resource.Status>()
    val loadingState: LiveData<Resource.Status> = _loadingState

    private val _latestMovie = MutableLiveData<Movie>()
    val latestMovie: LiveData<Movie> = _latestMovie

    private val _loadedSection = MutableLiveData<Section>()
    val loadedSection: LiveData<Section> = _loadedSection

    private val job = Job()
    override val coroutineContext: CoroutineContext = job + Dispatchers.IO

    fun requestUpdates() {
        launch { ui { _loadingState.postValue(Resource.Status.LOADING) } }
        launch { fetchPopular() }
        launch { fetchLatest() }
        launch { fetchUpcoming() }
    }

    private suspend fun fetchLatest() {
        moviesRepository.fetchLatest(true).collect {
            ui {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        val movie = it.data ?: return@ui
                        _latestMovie.postValue(movie)
                    }
                    Resource.Status.SUCCESS -> {
                        _loadingState.postValue(Resource.Status.SUCCESS)
                        val movie = it.data ?: return@ui
                        _latestMovie.postValue(movie)

                    }
                    Resource.Status.ERROR -> {
                        _loadingState.postValue(Resource.Status.ERROR)
                    }
                }
            }
        }
    }

    private suspend fun fetchPopular() {
        moviesRepository.fetchPopular(true).collect {
            ui {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        val popular = it.data ?: return@ui
                        _loadedSection.postValue(
                            Section(
                                POPULAR_INDEX,
                                movieResourceManager.popular,
                                popular
                            )
                        )
                    }
                    Resource.Status.SUCCESS -> {
                        _loadingState.postValue(Resource.Status.SUCCESS)
                        val popular = it.data ?: return@ui
                        _loadedSection.postValue(
                            Section(
                                POPULAR_INDEX,
                                movieResourceManager.popular,
                                popular
                            )
                        )
                    }
                    Resource.Status.ERROR -> {
                        _loadingState.postValue(Resource.Status.ERROR)
                    }
                }
            }
        }
    }

    private suspend fun fetchUpcoming() {
        moviesRepository.fetchUpcoming(true).collect {
            ui {
                when (it.status) {
                    Resource.Status.LOADING -> {
                        val upcoming = it.data ?: return@ui
                        _loadedSection.postValue(
                            Section(
                                UPCOMING_INDEX,
                                movieResourceManager.upcoming,
                                upcoming
                            )
                        )
                    }
                    Resource.Status.SUCCESS -> {
                        _loadingState.postValue(Resource.Status.SUCCESS)
                        val upcoming = it.data ?: return@ui
                        _loadedSection.postValue(
                            Section(
                                UPCOMING_INDEX,
                                movieResourceManager.upcoming,
                                upcoming
                            )
                        )
                    }
                    Resource.Status.ERROR -> {
                        _loadingState.postValue(Resource.Status.ERROR)
                    }
                }
            }
        }
    }

    companion object {
        private const val POPULAR_INDEX = 0
        private const val UPCOMING_INDEX = 1
    }
}

suspend fun ui(block: () -> Unit) {
    withContext(Dispatchers.Main) { block() }
}