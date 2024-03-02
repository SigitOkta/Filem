/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.paging

import androidx.paging.PagingSource
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieFilter
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1


/*
class MoviePagingSource(
    private val filter: MovieFilter,
    private val api: ApiService
) : PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = when (filter) {
                MovieFilter.NOW_PLAYING -> api.getNowPlaying(page)
                MovieFilter.UPCOMING -> api.getUpComing(page)
            }

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.total_pages || response.total_pages == 0) null else page + 1
            )

        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
}
*/
