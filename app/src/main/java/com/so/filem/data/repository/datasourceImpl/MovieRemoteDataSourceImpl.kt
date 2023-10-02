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
import com.so.filem.data.remote.asCastDetails
import com.so.filem.data.remote.asCasts
import com.so.filem.data.remote.asMovieDetails
import com.so.filem.data.remote.asMovies
import com.so.filem.data.remote.asTvs
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Casts
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
) : MovieRemoteDataSource {
    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return api.getMovieDetails(movieId).asMovieDetails()
    }

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