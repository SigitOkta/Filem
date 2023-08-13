package com.so.filem.data.repository.datasource

import com.so.filem.domain.model.movie.Movie
import kotlinx.coroutines.flow.Flow

interface MovieLocalDataSource {
    fun getMoviesFromDB(movieId : Int): Flow<Movie>
}