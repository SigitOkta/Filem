package com.so.filem.domain.usecase.movie

import javax.inject.Inject

data class GetMovieUseCase @Inject constructor(
    val getFilterMovieUseCase: GetFilterMovieUseCase,
    val getMovieFromDBUseCase: GetMovieFromDBUseCase,
)
