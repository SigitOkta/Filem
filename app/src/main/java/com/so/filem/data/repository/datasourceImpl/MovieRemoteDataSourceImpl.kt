package com.so.filem.data.repository.datasourceImpl

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.so.filem.data.paging.SearchPagingSource
import com.so.filem.data.remote.asCastDetails
import com.so.filem.data.remote.asCasts
import com.so.filem.data.remote.asMovieDetails
import com.so.filem.data.remote.asMovies
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Casts
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Search
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
) : MovieRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return api.getMovieDetails(movieId).asMovieDetails()
    }

    /*override fun getSearchMultiPaging(query: String){
        Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchMultiPagingSource(
                    query = query,
                    api = api
                )
            }
        ).flow
    }*/
    override fun getSearch(query: String, type: Int): LiveData<PagingData<Search>> {
        return getSearchPagingSource(query,type)
    }
    private fun getSearchPagingSource(query: String,type: Int) : LiveData<PagingData<Search>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                SearchPagingSource(
                    query = query,
                    api = api,
                    type = type
                )
            }
        ).liveData
    }

    override suspend fun getDiscoverMovie(): Movies {
        return api.getDiscoverMovie().asMovies()
    }

    override suspend fun getCastDetails(castId: Long): CastDetails {
        return api.getCastDetail(castId).asCastDetails()
    }

    override suspend fun getTrendingMovie(timeWindow: String): Movies {
        return api.getTrendingMovie(timeWindow).asMovies()
    }

    override suspend fun getPopularPeople(): Casts {
        return api.getPopularPeople().asCasts()
    }

}