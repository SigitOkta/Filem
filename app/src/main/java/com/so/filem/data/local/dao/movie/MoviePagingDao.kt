package com.so.filem.data.local.dao.movie

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviePagingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies: List<MoviePaging>)

    @Query("SELECT * FROM movies_paging")
    fun getAllMovies(): PagingSource<Int, MoviePaging>

    @Query("SELECT * FROM movies_paging WHERE id = :movieId")
    fun getMovie(movieId: Int): Flow<MoviePaging>

    @Query("DELETE FROM movies_paging")
    suspend fun deleteAllMovies()
}