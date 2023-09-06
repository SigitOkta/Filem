package com.so.filem.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.paging.MovieRemoteMediator
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.paging.SearchMoviesPagingSource
import com.so.filem.data.remote.asMovieDetails
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
) : MovieRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return api.getMovieDetails(movieId).asMovieDetails()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getSearchMoviePaging(query: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchMoviesPagingSource(
                    query = query,
                    api = api
                )
            }
        ).flow
    }

}