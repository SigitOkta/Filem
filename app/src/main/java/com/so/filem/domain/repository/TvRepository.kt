package com.so.filem.domain.repository

import androidx.paging.PagingData
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.TvFilter
import com.so.filem.domain.model.TvShow
import com.so.filem.domain.model.Tvs
import kotlinx.coroutines.flow.Flow

interface TvRepository {
    fun getTvShowsForPaging(filter: TvFilter): Flow<PagingData<TvPaging>>
    suspend fun getTvDetails(tvId: Long): TvDetails
    suspend fun discoverTv(): Tvs
    suspend fun getTrendingTv(timeWindow: String): Tvs
    suspend fun saveTvFavorite(tv: TvShow): Long
    fun getFavoriteTv(tvId: Long): Flow<TvShow?>
    fun getAllFavoriteTvs(): Flow<List<TvShow>>
    suspend fun updateTvFavorite(tvId: String, isFavorite: Boolean)
    suspend fun deleteFavoriteTv(tvId: Long): Int

}