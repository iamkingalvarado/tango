package io.tango.challenge.features.movies.domain.models

data class Section(
    val id: Int,
    val title: String,
    val movies: List<Movie>
)
