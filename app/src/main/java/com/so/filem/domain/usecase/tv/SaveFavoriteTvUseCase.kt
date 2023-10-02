package com.so.filem.domain.usecase.tv

import com.so.filem.domain.model.TvShow
import com.so.filem.domain.repository.TvRepository
import javax.inject.Inject

class SaveFavoriteTvUseCase @Inject constructor(
    private val tvRepository: TvRepository,
) {
    suspend operator fun invoke(tv: TvShow): Long {
        return tvRepository.saveTvFavorite(tv)
    }
}