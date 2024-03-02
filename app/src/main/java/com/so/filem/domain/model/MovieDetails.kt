/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.model

data class MovieDetails(
    val movie: Movie,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val trailers: List<Trailer>
)
