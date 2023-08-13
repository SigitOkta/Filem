package com.so.filem.domain.usecase.movie

import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetMovieFromDBUseCase @Inject constructor(private val movieRepository: MovieRepository) {
    operator fun invoke(movieID: Int) = movieRepository.getMoviesFromDB(movieID)
}