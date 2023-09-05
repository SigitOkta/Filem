package com.so.filem.domain.usecase.movie

import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class MovieExistsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Long): Boolean {
        return movieRepository.movieExists(movieId)
    }
}