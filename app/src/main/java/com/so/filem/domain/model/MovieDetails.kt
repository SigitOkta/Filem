package com.so.filem.domain.model

data class MovieDetails(
    val movie: Movie,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val trailers: List<Trailer>
)
