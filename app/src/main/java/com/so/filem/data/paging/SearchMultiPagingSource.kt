package com.so.filem.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.so.filem.data.remote.asSearchMulti
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.Search
import timber.log.Timber

private const val STARTING_PAGE_INDEX = 1
class SearchMultiPagingSource(
    private val query: String,
    private val api: ApiService,
) : PagingSource<Int, Search>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Search> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = api.getSearchMulti(query, page).asSearchMulti()
            if (response.results.isEmpty()){
                LoadResult.Error(Exception("No results found"))
            } else {
                LoadResult.Page(
                    data = response.results,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    // Avoid infinite loading when the search response total_pages is 0
                    nextKey = if (page == response.totalPages || response.totalPages == 0) null else page + 1
                )
            }
        } catch (e: Exception) {
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Search>): Int? {
        return state.anchorPosition
    }

}