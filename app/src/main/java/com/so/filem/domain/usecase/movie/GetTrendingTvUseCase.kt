package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetTrendingTvUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(timeWindow : String): Tvs {
        return movieRepository.getTrendingTv(timeWindow)
    }
}