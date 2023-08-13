package com.so.filem.data.repository.datasourceImpl

import com.so.filem.data.local.dao.movie.MovieDao
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(private val movieDao: MovieDao) :
    MovieLocalDataSource {
    override fun getMoviesFromDB(movieId: Int): Flow<Movie> = movieDao.getMovie(movieId)
}