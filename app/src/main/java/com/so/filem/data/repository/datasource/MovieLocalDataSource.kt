/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository.datasource

import androidx.paging.PagingData
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import com.so.filem.domain.model.MovieFilter
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMoviesFromDB(movieId : Int): Flow<MoviePaging>

    fun getMoviesForPaging(filter: MovieFilter): Flow<PagingData<MoviePaging>>

    suspend fun saveFavorite(movie: MoviesEntity): Long
    fun getFavoriteMovie(movieId: Long): Flow<MoviesEntity?>
    fun getAllFavoriteMovies(): Flow<List<MoviesEntity>>
    suspend fun updateFavorite(movieId: String, isFavorite:Boolean)
    suspend fun deleteFavoriteMovie(movieId: Long): Int
    suspend fun deleteAllFavoriteMovie()

    suspend fun deleteMoviesWithNoFav(): Int

    suspend fun movieExists(id: Long): Boolean
}