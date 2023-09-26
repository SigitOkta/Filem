package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Tv
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetDiscoverTvUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Tv> {
        return movieRepository.discoverTv().results
    }
}