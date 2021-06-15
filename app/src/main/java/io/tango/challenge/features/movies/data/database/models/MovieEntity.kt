package io.tango.challenge.features.movies.data.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Movie")
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long = -1,
    val title: String,
    val description: String,
    @ColumnInfo(name = "poster_url")
    val posterUrl: String?,
    @ColumnInfo(name = "hero_url")
    val heroUrl: String?,
    @ColumnInfo(name = "is_latest")
    val isLatest: Boolean = false,
    @ColumnInfo(name = "is_popular")
    val isPopular: Boolean = false,
    @ColumnInfo(name = "is_upcoming")
    val isUpcoming: Boolean = false,
    @ColumnInfo(name = "release_date")
    val releaseDate: String
)
