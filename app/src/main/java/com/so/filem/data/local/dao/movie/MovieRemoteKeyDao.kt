package com.so.filem.data.local.dao.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.filem.domain.model.movie.MovieRemoteKey

@Dao
interface MovieRemoteKeyDao {
    @Query("SELECT * FROM movie_remote_key WHERE id = :movieId")
    fun getMovieRemoteKeys(movieId: Int): MovieRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMovieRemoteKeys(movieRemoteKeys : List<MovieRemoteKey>)

    @Query("DELETE FROM movie_remote_key")
    fun deleteAllMovieRemoteKeys()
}