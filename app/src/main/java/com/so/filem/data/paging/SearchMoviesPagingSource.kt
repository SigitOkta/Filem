package com.so.filem.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.so.filem.data.remote.asMovies
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.Movie
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
class SearchMoviesPagingSource(
    private val query: String,
    private val api: ApiService,
) : PagingSource<Int, Movie>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getMovieSearch(query, page).asMovies()

            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                // Avoid infinite loading when the search response total_pages is 0
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