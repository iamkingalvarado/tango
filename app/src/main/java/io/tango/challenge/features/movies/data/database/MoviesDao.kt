package io.tango.challenge.features.movies.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.tango.challenge.features.movies.data.database.models.MovieEntity

@Dao
interface MoviesDao {
    @Query("SELECT * from movie where is_latest = 1 LIMIT 1")
    suspend fun getLatest(): MovieEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveLatest(movie: MovieEntity)

    @Query("SELECT * from movie where is_popular = 1")
    suspend fun fetchPopular(): List<MovieEntity>

    @Query("SELECT * from movie where is_upcoming = 1")
    suspend fun fetchUpcoming(): List<MovieEntity>
}
