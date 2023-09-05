package com.so.filem.domain.usecase.movie

import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteMoviesWithNoFavUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(): Int {
        return movieRepository.deleteMoviesWithNoFav()
    }
}