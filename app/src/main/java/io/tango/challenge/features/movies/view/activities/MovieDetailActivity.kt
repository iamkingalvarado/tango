package io.tango.challenge.features.movies.view.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import io.tango.challenge.R
import io.tango.challenge.databinding.ActivityMovieDetailBinding
import io.tango.challenge.extensions.CornerType
import io.tango.challenge.extensions.loadUrlImage
import io.tango.challenge.extensions.viewBinding
import io.tango.challenge.features.movies.domain.models.Movie

@AndroidEntryPoint
class MovieDetailActivity : AppCompatActivity(R.layout.activity_movie_detail) {

    private val binding by viewBinding(ActivityMovieDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val movie = intent.getSerializableExtra(EXTRA_MOVIE) as Movie
        setupView(movie)
    }

    private fun setupView(movie: Movie) {
        binding.movieBannerImageView.loadUrlImage(movie.heroUrl ?: movie.posterUrl, isBlur = true)
        binding.moviePosterImageView.loadUrlImage(movie.posterUrl, cornerType = CornerType.All(8))
        binding.movieTitleTextView.text = movie.title
        binding.movieFootnoteTextView.text = movie.releaseDate
        binding.movieSynopsisTextView.text = movie.description
    }

    companion object {
        const val EXTRA_MOVIE = "EXTRA_MOVIE"
    }
}
