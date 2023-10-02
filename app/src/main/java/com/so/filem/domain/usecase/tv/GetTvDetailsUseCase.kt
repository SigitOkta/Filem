package com.so.filem.domain.usecase.tv

import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.repository.MovieRepository
import com.so.filem.domain.repository.TvRepository
import javax.inject.Inject

class GetTvDetailsUseCase @Inject constructor(
    private val tvRepository: TvRepository,
) {
    suspend operator fun invoke(tvId: Long): TvDetails {
        return tvRepository.getTvDetails(tvId)
    }
}