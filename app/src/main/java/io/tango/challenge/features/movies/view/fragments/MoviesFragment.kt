package io.tango.challenge.features.movies.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import dagger.hilt.android.AndroidEntryPoint
import io.tango.challenge.R
import io.tango.challenge.core.data.Resource
import io.tango.challenge.core.domain.managers.SkeletonManager
import io.tango.challenge.databinding.FragmentMoviesBinding
import io.tango.challenge.extensions.observe
import io.tango.challenge.extensions.viewBinding
import io.tango.challenge.features.movies.domain.models.Movie
import io.tango.challenge.features.movies.view.activities.MovieDetailActivity
import io.tango.challenge.features.movies.view.adapters.LatestMovieAdapter
import io.tango.challenge.features.movies.view.adapters.SectionMoviesAdapter
import io.tango.challenge.features.movies.view.viewmodels.MoviesViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {

    private val viewModel: MoviesViewModel by viewModels()

    @Inject
    lateinit var skeletonManager: SkeletonManager

    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val latestMovieAdapter = LatestMovieAdapter()
    private val sectionMoviesAdapter = SectionMoviesAdapter { handleMovieClick(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureView()
        initObservations()
        viewModel.requestUpdates()
    }

    private fun initObservations() {
        observe(viewModel.loadingState) {
            when (it) {
                Resource.Status.SUCCESS -> {
                    skeletonManager.hideSkeleton()
                }
                Resource.Status.ERROR -> {

                    Toast.makeText(
                        requireContext(),
                        R.string.dialog_error_loading_movies,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                Resource.Status.LOADING -> skeletonManager.showSkeleton(
                    binding.moviesLayout,
                    R.layout.view_loading_fragment_movies
                )
            }
        }

        observe(viewModel.loadedSection) {
            sectionMoviesAdapter.add(it)
        }

        observe(viewModel.latestMovie) {
            latestMovieAdapter.update(it)
        }
    }

    private fun handleMovieClick(movie: Movie) {
        startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
            putExtra(MovieDetailActivity.EXTRA_MOVIE, movie)
        })
    }

    private fun configureView() {
        binding.recyclerView.adapter = ConcatAdapter(latestMovieAdapter, sectionMoviesAdapter)
    }

    companion object {
        fun newInstance() = MoviesFragment()
    }
}
