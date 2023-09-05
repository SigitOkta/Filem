package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Movie
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class DeleteFavoriteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Long): Int {
        return movieRepository.deleteFavoriteMovie(movieId)
    }
}