package com.so.filem.data.repository

import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {
    override fun getMoviesForPaging(filter: MovieFilter) = localDataSource.getMoviesForPaging(filter)
    override fun getMoviesFromDB(movieId: Int): Flow<MoviePaging> {
        return localDataSource.getMoviesFromDB(movieId)
    }


}