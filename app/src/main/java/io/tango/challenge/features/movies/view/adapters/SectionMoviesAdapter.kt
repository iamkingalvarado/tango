package io.tango.challenge.features.movies.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.tango.challenge.databinding.ItemSectionBinding
import io.tango.challenge.features.movies.domain.models.Movie
import io.tango.challenge.features.movies.domain.models.Section

class SectionMoviesAdapter(
    private val movieClickListener: (movie: Movie) -> Unit
) : RecyclerView.Adapter<SectionMoviesAdapter.SectionViewHolder>() {

    private var sections: ArrayList<Section> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionViewHolder {
        val binding = ItemSectionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SectionViewHolder(binding, movieClickListener)
    }

    override fun onBindViewHolder(holder: SectionViewHolder, position: Int) {
        val section = this.sections[position]
        holder.bind(section)
    }

    override fun getItemCount(): Int {
        return sections.size
    }

    fun add(section: Section) {
        val index = this.sections.indexOfFirst { it.id == section.id }
        if (index != -1) {
            this.sections[index] = section
            notifyItemChanged(index)
        } else {
            this.sections.add(section)
            notifyItemInserted(this.sections.size - 1)
        }
    }

    class SectionViewHolder(
        private val binding: ItemSectionBinding,
        movieClickListener: (movie: Movie) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        private val adapter = MovieAdapter(movieClickListener)

        init {
            binding.recyclerView.adapter = adapter
            binding.recyclerView.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
        }

        fun bind(section: Section) {
            binding.titleTextView.text = section.title
            adapter.update(section.movies)
        }
    }
}
