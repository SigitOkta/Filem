package com.so.filem.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.paging.MovieRemoteMediator
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.MovieFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val api: ApiService,
    private val db: TMDBDatabase,
) : MovieLocalDataSource {
    private val movieDao = db.moviePagingDao()
    override fun getMoviesFromDB(movieId: Int): Flow<MoviePaging> = movieDao.getMovie(movieId)

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
    override fun getMoviesForPaging(filter : MovieFilter): Flow<PagingData<MoviePaging>> {
        val pagingSourceFactory = { movieDao.getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MovieRemoteMediator(
                api,
                db,
                filter
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }
}