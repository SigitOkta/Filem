package com.so.filem.data.repository.datasource

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.MovieDetails
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {
    suspend fun getMovieDetails(movieId: Long) : MovieDetails
}