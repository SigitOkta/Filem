package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository,
) {
    suspend operator fun invoke(movieId: Long): MovieDetails {
        return movieRepository.getMovieDetails(movieId)
    }
}