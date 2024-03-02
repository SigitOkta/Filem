/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.domain.model.TvFilter
import com.so.filem.domain.repository.TvRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilterTvUseCase @Inject constructor(
    private val tvRepository: TvRepository
) {
    operator fun invoke(filter: TvFilter): Flow<PagingData<TvPaging>> {
        return tvRepository.getTvShowsForPaging(filter)
    }
}