package io.tango.challenge.features.movies.di

import androidx.fragment.app.Fragment
import io.tango.challenge.features.movies.data.repositories.CachedMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.tango.challenge.BuildConfig
import io.tango.challenge.core.data.Mapper
import io.tango.challenge.core.data.managers.ShimmerLayoutSkeletonManager
import io.tango.challenge.core.domain.managers.SkeletonManager
import io.tango.challenge.features.movies.data.database.MoviesDao
import io.tango.challenge.features.movies.data.database.MoviesDatabase
import io.tango.challenge.features.movies.data.database.mappers.MovieEntityMapper
import io.tango.challenge.features.movies.data.database.models.MovieEntity
import io.tango.challenge.features.movies.data.network.MoviesApi
import io.tango.challenge.features.movies.data.network.mappers.MovieResponseMapper
import io.tango.challenge.features.movies.data.network.models.MovieResponse
import io.tango.challenge.features.movies.domain.managers.MovieResourceManager
import io.tango.challenge.features.movies.domain.models.Movie
import io.tango.challenge.features.movies.domain.repositories.MoviesRepository
import io.tango.challenge.features.movies.view.fragments.MoviesFragment
import io.tango.challenge.features.movies.view.managers.AndroidMovieResourceManager
import retrofit2.Retrofit

@Suppress("unused")
@InstallIn(FragmentComponent::class)
@Module
abstract class MoviesModule {

    @Binds
    abstract fun bindSkeletonManager(skeletonManager: ShimmerLayoutSkeletonManager): SkeletonManager
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelMovieModule {

    @MovieDBMapper
    @Binds
    abstract fun bindMovieEntityMapper(movieEntityMapper: MovieEntityMapper):
            Mapper<MovieEntity, Movie>

    @Binds
    @ViewModelScoped
    abstract fun bindMoviesRepository(
        cachedMoviesRepository: CachedMoviesRepository
    ): MoviesRepository

    @Binds
    @ViewModelScoped
    abstract fun bindMovieResourceManager(movieResourceManager: AndroidMovieResourceManager): MovieResourceManager
}

@Module
@InstallIn(ViewModelComponent::class)
object MoviesViewModelModule {
    @Provides
    fun provideMoviesDao(database: MoviesDatabase): MoviesDao {
        return database.moviesDao()
    }

    @Provides
    fun provideMoviesApi(retrofit: Retrofit): MoviesApi {
        return retrofit.create(MoviesApi::class.java)
    }

    @MovieAPIMapper
    @Provides
    fun provideMovieResponseMapper(): Mapper<MovieResponse, Movie> {
        return MovieResponseMapper(baseImageUrl = BuildConfig.BASE_IMAGE_URL)
    }
}

@InstallIn(FragmentComponent::class)
@Module
object MoviesFragmentModule {

    @Provides
    fun provideMoviesFragment(fragment: Fragment): MoviesFragment {
        return fragment as MoviesFragment
    }


}