/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.domain.model.Search
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchMultiUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(query: String, type: Int) : Flow<PagingData<Search>> {
        return movieRepository.getSearch(query, type)
    }
}