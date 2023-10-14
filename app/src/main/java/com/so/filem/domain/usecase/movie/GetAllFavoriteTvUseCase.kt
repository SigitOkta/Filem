package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.TvShow
import com.so.filem.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteTvUseCase @Inject constructor(
    private val tvRepository: TvRepository
) {
    operator fun invoke(): Flow<List<TvShow>> {
        return tvRepository.getAllFavoriteTvs()
    }
}