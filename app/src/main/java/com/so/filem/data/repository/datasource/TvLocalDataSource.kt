package com.so.filem.data.repository.datasource

import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import kotlinx.coroutines.flow.Flow

interface TvLocalDataSource {
    suspend fun saveTvFavorite(tv: TvsEntity): Long
    fun getFavoriteTv(tvId: Long): Flow<TvsEntity?>
    fun getAllFavoriteTvs(): Flow<List<TvsEntity>>
    suspend fun updateTvFavorite(tvId: String, isFavorite: Boolean)
    suspend fun deleteFavoriteTv(tvId: Long): Int

}