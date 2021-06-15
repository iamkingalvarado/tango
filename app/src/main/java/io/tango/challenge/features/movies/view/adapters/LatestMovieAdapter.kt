package io.tango.challenge.features.movies.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import io.tango.challenge.databinding.ItemLatestMovieBinding
import io.tango.challenge.extensions.CornerType
import io.tango.challenge.extensions.loadUrlImage
import io.tango.challenge.features.movies.domain.models.Movie

class LatestMovieAdapter : RecyclerView.Adapter<LatestMovieAdapter.LatestMovieViewHolder>() {

    private var movie: Movie? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LatestMovieViewHolder {
        val binding = ItemLatestMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LatestMovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LatestMovieViewHolder, position: Int) {
        movie?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return movie?.let { 1 } ?: 0
    }

    fun update(movie: Movie) {
        this.movie = movie
        notifyDataSetChanged()
    }

    class LatestMovieViewHolder(private val binding: ItemLatestMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.noImageTextView.isVisible = movie.heroUrl == null && movie.posterUrl == null
            binding.imageView.isVisible = movie.heroUrl != null || movie.posterUrl != null
            binding.imageView.loadUrlImage(movie.heroUrl ?: movie.posterUrl, cornerType = CornerType.All(8))
        }
    }
}
