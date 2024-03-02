/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Cast
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Cast> {
        return movieRepository.getPopularPeople().results
    }
}