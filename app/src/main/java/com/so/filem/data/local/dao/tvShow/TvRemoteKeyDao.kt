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
import com.so.filem.data.local.dao.tvShow.entity.TvRemoteKey

@Dao
interface TvRemoteKeyDao {
    @Query("SELECT * FROM tv_remote_key WHERE id = :tvId")
    suspend fun getTvRemoteKeys(tvId: Long): TvRemoteKey?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllTvRemoteKeys(tvRemoteKey : List<TvRemoteKey>)

    @Query("DELETE FROM tv_remote_key")
    suspend fun deleteAllTvRemoteKeys()
}