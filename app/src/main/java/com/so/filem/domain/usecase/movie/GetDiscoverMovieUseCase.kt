package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.Movies
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetDiscoverMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Movie> {
        return movieRepository.discoverMovie().results
    }
}