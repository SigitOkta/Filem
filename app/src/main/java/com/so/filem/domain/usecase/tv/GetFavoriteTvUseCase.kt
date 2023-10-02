package com.so.filem.domain.usecase.tv

import com.so.filem.domain.model.TvShow
import com.so.filem.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteTvUseCase @Inject constructor(
    private val tvRepository: TvRepository,
) {
    operator fun invoke(tvId: Long): Flow<TvShow?> {
        return tvRepository.getFavoriteTv(tvId)
    }
}