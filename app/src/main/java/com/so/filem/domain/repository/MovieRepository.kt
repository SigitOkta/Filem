package com.so.filem.domain.repository

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesForPaging(filter: MovieFilter): Flow<PagingData<MoviePaging>>
    fun getMoviesFromDB(movieId: Int): Flow<MoviePaging>
}
