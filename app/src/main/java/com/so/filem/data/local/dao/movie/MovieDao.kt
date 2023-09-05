package com.so.filem.data.local.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MoviesEntity): Long

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Long): Flow<MoviesEntity?>

    @Query("SELECT * FROM movies WHERE isFavorite = 1 ORDER BY title")
    fun getAllFavoriteMovies(): Flow<List<MoviesEntity>>

    @Query("UPDATE movies SET isFavorite = :isFavorite WHERE id = :movieId")
    suspend fun updateFavorite(movieId: String, isFavorite: Boolean)

    @Query("DELETE FROM movies WHERE id = :movieId")
    suspend fun deleteMovieById(movieId: Long): Int
    @Query("DELETE FROM movies WHERE isFavorite = 0")
    suspend fun deleteMoviesWithNoFav() : Int
    @Query("SELECT EXISTS (SELECT * FROM movies WHERE id=:id AND isFavorite = 1)")
    suspend fun movieExists(id: Long): Boolean
    @Query("DELETE FROM movies")
    suspend fun deleteMovies()
}