package io.tango.challenge.features.movies.domain.models

import java.io.Serializable

data class Movie(
    val id: Long,
    val title: String,
    val description: String,
    val posterUrl: String?,
    val heroUrl: String?,
    val releaseDate: String,
    var isLatest: Boolean = false,
    var isPopular: Boolean = false,
    var isUpcoming: Boolean = false
) : Serializable
