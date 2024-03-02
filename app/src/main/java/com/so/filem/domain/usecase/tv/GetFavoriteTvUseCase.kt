/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

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