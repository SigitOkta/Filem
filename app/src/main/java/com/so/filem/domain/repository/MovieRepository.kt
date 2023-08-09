package com.so.filem.domain.repository

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovies(filter: MovieFilter): Flow<PagingData<Movie>>
}
