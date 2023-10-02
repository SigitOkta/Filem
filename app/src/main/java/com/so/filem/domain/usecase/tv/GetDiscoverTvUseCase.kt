package com.so.filem.domain.usecase.tv

import com.so.filem.domain.model.TvShow
import com.so.filem.domain.repository.MovieRepository
import com.so.filem.domain.repository.TvRepository
import javax.inject.Inject

class GetDiscoverTvUseCase @Inject constructor(
    private val tvRepository: TvRepository
) {
    suspend operator fun invoke(): List<TvShow> {
        return tvRepository.discoverTv().results
    }
}