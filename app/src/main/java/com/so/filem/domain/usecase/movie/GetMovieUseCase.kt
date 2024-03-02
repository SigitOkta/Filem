/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.usecase.movie

import javax.inject.Inject

data class GetMovieUseCase @Inject constructor(
    val getFilterMovieUseCase: GetFilterMovieUseCase,
    val getMovieFromDBUseCase: GetMovieFromDBUseCase,
)
