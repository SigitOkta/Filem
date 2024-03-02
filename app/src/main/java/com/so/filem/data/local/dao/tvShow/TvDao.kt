/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.local.dao.tvShow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TvDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTv(tvShow: TvsEntity): Long

    @Query("SELECT * FROM tvShows WHERE id = :tvShowId")
    fun getTv(tvShowId: Long): Flow<TvsEntity?>

    @Query("SELECT * FROM tvShows WHERE isFavorite = 1 ORDER BY name")
    fun getAllFavoriteTvShows(): Flow<List<TvsEntity>>

    @Query("UPDATE tvShows SET isFavorite = :isFavorite WHERE id = :tvShowId")
    suspend fun updateFavorite(tvShowId: String, isFavorite: Boolean)

    @Query("DELETE FROM tvShows WHERE id = :tvShowId")
    suspend fun deleteTvById(tvShowId: Long): Int
    @Query("DELETE FROM tvShows WHERE isFavorite = 0")
    suspend fun deleteTvsWithNoFav() : Int
    @Query("SELECT EXISTS (SELECT * FROM tvShows WHERE id=:id AND isFavorite = 1)")
    suspend fun tvExists(id: Long): Boolean
    @Query("DELETE FROM tvShows")
    suspend fun deleteTvs()
}