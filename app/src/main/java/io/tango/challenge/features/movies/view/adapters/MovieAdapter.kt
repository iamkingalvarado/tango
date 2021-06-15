package io.tango.challenge.features.movies.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.tango.challenge.databinding.ItemMovieBinding
import io.tango.challenge.extensions.CornerType
import io.tango.challenge.extensions.loadUrlImage
import io.tango.challenge.features.movies.domain.models.Movie

class MovieAdapter(
    private val clickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    private var movies: List<Movie> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding) { clickListener.invoke(movies[it]) }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    fun update(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    class MovieViewHolder(
        private val binding: ItemMovieBinding,
        clickListener: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { clickListener.invoke(bindingAdapterPosition) }
        }
        fun bind(movie: Movie) {
            binding.imageView.loadUrlImage(movie.posterUrl, cornerType = CornerType.All(6))
        }
    }
}
