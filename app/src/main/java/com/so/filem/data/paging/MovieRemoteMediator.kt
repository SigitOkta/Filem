package com.so.filem.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.model.movie.MovieRemoteKey
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException
import java.util.Currency

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val api: ApiService,
    private val db: TMDBDatabase,
    private val filter: MovieFilter
) : RemoteMediator<Int, Movie>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Movie>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH ->{
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextPage?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.prevPage
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
        }

        try {
            val response = when (filter) {
                MovieFilter.NOW_PLAYING -> api.getNowPlaying(page)
                MovieFilter.UPCOMING -> api.getUpComing(page)
            }

            val repos = response.results
            val endOfPaginationReached = repos.isEmpty()
            db.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    db.movieRemoteKeysDao().deleteAllMovieRemoteKeys()
                    db.movieDao().deleteAllMovies()
                }
                val prevKey = if (page == INITIAL_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = repos.map {
                    MovieRemoteKey(
                        id = it.id,
                        prevPage = prevKey,
                        nextPage = nextKey,
                        lastUpdated = System.currentTimeMillis()
                    )
                }
                db.movieRemoteKeysDao().addAllMovieRemoteKeys(keys)
                db.movieDao().addMovies(repos)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Movie>): MovieRemoteKey? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                db.movieRemoteKeysDao().getMovieRemoteKeys(id)
            }
        }
    }
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Movie>,
    ): MovieRemoteKey? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { movie ->
                db.movieRemoteKeysDao().getMovieRemoteKeys(movieId = movie.id)
            }
    }

    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Movie>,
    ): MovieRemoteKey? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movie ->
                db.movieRemoteKeysDao().getMovieRemoteKeys(movieId = movie.id)
            }
    }
}