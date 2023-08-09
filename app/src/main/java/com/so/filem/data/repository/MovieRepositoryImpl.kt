package com.so.filem.data.repository

import androidx.paging.PagingData
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource
) : MovieRepository {
    override fun getMovies(filter: MovieFilter) = remoteDataSource.getMovies(filter)
}