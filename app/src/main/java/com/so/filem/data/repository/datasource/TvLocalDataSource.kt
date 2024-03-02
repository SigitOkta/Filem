/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository.datasource

import androidx.paging.PagingData
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import com.so.filem.domain.model.TvFilter
import kotlinx.coroutines.flow.Flow

interface TvLocalDataSource {
    fun getTvShowsForPaging(filter: TvFilter): Flow<PagingData<TvPaging>>
    suspend fun saveTvFavorite(tv: TvsEntity): Long
    fun getFavoriteTv(tvId: Long): Flow<TvsEntity?>
    fun getAllFavoriteTvs(): Flow<List<TvsEntity>>
    suspend fun updateTvFavorite(tvId: String, isFavorite: Boolean)
    suspend fun deleteFavoriteTv(tvId: Long): Int

}