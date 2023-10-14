package com.so.filem.data.local.dao.tvShow

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import kotlinx.coroutines.flow.Flow

@Dao
interface TvPagingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTvShows(tv: List<TvPaging>)

    @Query("SELECT * FROM tv_paging")
    fun getAllTvShows(): PagingSource<Int, TvPaging>

    @Query("SELECT * FROM tv_paging WHERE id = :tvId")
    fun getTv(tvId: Int): Flow<TvPaging>

    @Query("DELETE FROM tv_paging")
    suspend fun deleteAllTvs()
}