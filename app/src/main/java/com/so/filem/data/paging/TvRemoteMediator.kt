/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.data.local.dao.tvShow.entity.TvRemoteKey
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.TvFilter

@OptIn(ExperimentalPagingApi::class)
class TvRemoteMediator (
    private val api: ApiService,
    private val db: TMDBDatabase,
    private val filter: TvFilter
) : RemoteMediator<Int,TvPaging>(){

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    private val tvPagingDao = db.tvPagingDao()
    private val tvRemoteKeyDao = db.tvRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TvPaging>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1) ?: INITIAL_PAGE_INDEX
                }

                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevPage = remoteKeys?.prevPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    prevPage
                }

                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextPage = remoteKeys?.nextPage
                        ?: return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys != null
                        )
                    nextPage
                }
            }
            val response = when (filter) {
                TvFilter.AIRING_TODAY -> api.getAiringToday(page)
                TvFilter.ON_THE_AIR -> api.getOnTheAir(page)
                TvFilter.POPULAR -> api.getPopularTv(page)
                TvFilter.TOP_RATED -> api.getTopRatedTv(page)
            }

            val repos = response.results
            repos.sortedBy { it.id }
            val endOfPaginationReached = repos.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    tvRemoteKeyDao.deleteAllTvRemoteKeys()
                    tvPagingDao.deleteAllTvs()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    TvRemoteKey(
                        id = it.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                val responseResult = repos.map{
                    TvPaging(
                        id = it.id,
                        adult = it.adult,
                        backdropPath = it.backdrop_path,
                        originalLanguage = it.original_language,
                        originalName = it.original_name,
                        overview = it.overview,
                        posterPath = it.poster_path,
                        firstAirDate = it.first_air_date,
                        name = it.name,
                        voteAverage = it.vote_average,
                        voteCount = it.vote_count
                    )
                }
                tvRemoteKeyDao.addAllTvRemoteKeys(keys)
                tvPagingDao.addTvShows(responseResult)
            }
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, TvPaging>): TvRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                tvRemoteKeyDao.getTvRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, TvPaging>,
    ): TvRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { tv ->
                tvRemoteKeyDao.getTvRemoteKeys(tvId = tv.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, TvPaging>,
    ): TvRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { tv ->
                tvRemoteKeyDao.getTvRemoteKeys(tvId = tv.id)
            }
    }
}