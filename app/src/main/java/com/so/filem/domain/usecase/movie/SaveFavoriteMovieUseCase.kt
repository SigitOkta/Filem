/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Movie
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class SaveFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movie: Movie): Long {
        return movieRepository.saveFavoriteMovie(movie )
    }
}