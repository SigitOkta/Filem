package com.so.filem.domain.usecase.tv

import com.so.filem.domain.repository.MovieRepository
import com.so.filem.domain.repository.TvRepository
import javax.inject.Inject

class DeleteFavoriteTvUseCase @Inject constructor(
    private val tvRepository: TvRepository,
) {
    suspend operator fun invoke(tvId: Long): Int {
        return tvRepository.deleteFavoriteTv(tvId)
    }
}