package com.so.filem.data.repository.datasource

import androidx.paging.PagingData
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.MovieFilter
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMoviesFromDB(movieId : Int): Flow<MoviePaging>

    fun getMoviesForPaging(filter: MovieFilter): Flow<PagingData<MoviePaging>>
}