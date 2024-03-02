/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository

import androidx.paging.PagingData
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import com.so.filem.data.local.dao.tvShow.entity.asTv
import com.so.filem.data.local.dao.tvShow.entity.asTvs
import com.so.filem.data.repository.datasource.TvLocalDataSource
import com.so.filem.data.repository.datasource.TvRemoteDataSource
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.TvFilter
import com.so.filem.domain.model.TvShow
import com.so.filem.domain.model.Tvs
import com.so.filem.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TvRepositoryImpl @Inject constructor(
    private val tvRemoteDataSource: TvRemoteDataSource,
    private val tvLocalDataSource: TvLocalDataSource
) : TvRepository {
    override fun getTvShowsForPaging(filter: TvFilter): Flow<PagingData<TvPaging>> {
        return tvLocalDataSource.getTvShowsForPaging(filter)
    }

    override suspend fun getTvDetails(tvId: Long): TvDetails {
        return tvRemoteDataSource.getTvDetails(tvId)
    }

    override suspend fun discoverTv(): Tvs {
        return tvRemoteDataSource.getDiscoverTv()
    }

    override suspend fun getTrendingTv(timeWindow: String): Tvs {
        return tvRemoteDataSource.getTrendingTv(timeWindow)
    }

    override suspend fun saveTvFavorite(tv: TvShow): Long {
        return tvLocalDataSource.saveTvFavorite(
            TvsEntity(
                id = tv.id,
                adult = tv.adult,
                backdrop_path = tv.backdropPath,
                original_language = tv.original_language,
                original_name = tv.original_name,
                overview = tv.overview,
                poster_path = tv.posterPath,
                first_air_date = tv.first_air_date,
                name = tv.name,
                vote_average = tv.vote_average,
                vote_count = tv.vote_count,
                isFavorite = true
            )
        )
    }

    override fun getFavoriteTv(tvId: Long): Flow<TvShow?> {
       return tvLocalDataSource.getFavoriteTv(tvId).map { it?.asTv() }
    }

    override fun getAllFavoriteTvs(): Flow<List<TvShow>> {
        return tvLocalDataSource.getAllFavoriteTvs().map { it.asTvs() }
    }

    override suspend fun updateTvFavorite(tvId: String, isFavorite: Boolean) {
        return tvLocalDataSource.updateTvFavorite(tvId,isFavorite)
    }

    override suspend fun deleteFavoriteTv(tvId: Long): Int {
        return tvLocalDataSource.deleteFavoriteTv(tvId)
    }

}