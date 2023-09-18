package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movie
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetCastDetailsUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(cashId : Long): CastDetails {
        return movieRepository.getCastDetail(cashId)
    }
}