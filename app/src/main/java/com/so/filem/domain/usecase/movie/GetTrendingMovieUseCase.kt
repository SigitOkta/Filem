package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(timeWindow : String): Movies {
        return movieRepository.getTrendingMovie(timeWindow)
    }
}