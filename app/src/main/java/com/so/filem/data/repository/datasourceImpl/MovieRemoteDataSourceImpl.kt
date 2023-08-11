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
import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
    private val db: TMDBDatabase,
) : MovieRemoteDataSource {
    private val movieDao = db.movieDao()

    /*override fun getMovies(filter: MovieFilter): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    api = api,
                    filter = filter
                )
            }
        ).flow
    }*/

     @OptIn(ExperimentalPagingApi::class)
     override fun getMovies(filter : MovieFilter): Flow<PagingData<Movie>> {
         val pagingSourceFactory = { movieDao.getAllMovies() }
         return Pager(
             config = PagingConfig(pageSize = 20),
             remoteMediator = MovieRemoteMediator(
                 api,
                 db,
                 filter
             ),
             pagingSourceFactory = pagingSourceFactory,
         ).flow
     }


}