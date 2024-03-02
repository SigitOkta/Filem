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
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MovieRemoteKey

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: ApiService,
    private val db: TMDBDatabase,
    private val filter: MovieFilter
) : RemoteMediator<Int, MoviePaging>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    private val moviePagingDao = db.moviePagingDao()
    private val movieRemoteKeysDao = db.movieRemoteKeysDao()

    override suspend fun load(loadType: LoadType, state: PagingState<Int, MoviePaging>): MediatorResult {
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
                MovieFilter.NOW_PLAYING -> api.getNowPlaying(page)
                MovieFilter.UPCOMING -> api.getUpComing(page)
                MovieFilter.POPULAR -> api.getPopular(page)
                MovieFilter.TOP_RATED -> api.getTopRated(page)
            }

            val repos = response.results
            repos.sortedBy { it.id }
            val endOfPaginationReached = repos.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    movieRemoteKeysDao.deleteAllMovieRemoteKeys()
                    moviePagingDao.deleteAllMovies()
                }
                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    MovieRemoteKey(
                        id = it.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                val responseResult = repos.map{
                    MoviePaging(
                        id = it.id,
                        adult = it.adult,
                        backdrop_path = it.backdrop_path,
                        original_language = it.original_language,
                        original_title = it.original_title,
                        overview = it.overview,
                        popularity = it.popularity,
                        poster_path = it.poster_path,
                        release_date = it.release_date,
                        title = it.title,
                        vote_average = it.vote_average,
                        vote_count = it.vote_count
                    )
                }
                movieRemoteKeysDao.addAllMovieRemoteKeys(keys)
                moviePagingDao.addMovies(responseResult)
            }

            /*var endOfPaginationReached = false
            if (response.isSuccessful) {
                val responseData = response.body()
                endOfPaginationReached = responseData == null
                responseData?.let {
                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) {
                            movieDao.deleteAllMovies()
                            movieRemoteKeysDao.deleteAllMovieRemoteKeys()
                        }
                        var prevPage: Int?
                        var nextPage: Int

                        responseData.page.let { pageNumber ->
                            nextPage = pageNumber + 1
                            prevPage = if (pageNumber <= 1) null else pageNumber - 1
                        }

                        val keys = responseData.results.map { movie ->
                            MovieRemoteKey(
                                id = movie.id,
                                prevPage = prevPage,
                                nextPage = nextPage,
                                lastUpdated = System.currentTimeMillis()
                            )
                        }
                        movieRemoteKeysDao.addAllMovieRemoteKeys(movieRemoteKeys = keys)
                        movieDao.addMovies(movies = responseData.results)
                    }
                }

            }*/
            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, MoviePaging>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                movieRemoteKeysDao.getMovieRemoteKeys(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, MoviePaging>,
    ): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, MoviePaging>,
    ): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                movieRemoteKeysDao.getMovieRemoteKeys(movieId = movie.id)
            }
    }
}