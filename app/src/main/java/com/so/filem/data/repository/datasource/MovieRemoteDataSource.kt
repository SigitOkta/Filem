package com.so.filem.data.repository.datasource

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    fun getMovies(filter: MovieFilter): Flow<PagingData<Movie>>
}