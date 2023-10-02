package com.so.filem.domain.usecase.tv

import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import com.so.filem.domain.repository.MovieRepository
import com.so.filem.domain.repository.TvRepository
import javax.inject.Inject

class GetTrendingTvUseCase @Inject constructor(
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(timeWindow : String): Tvs {
        return tvRepository.getTrendingTv(timeWindow)
    }
}