/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

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