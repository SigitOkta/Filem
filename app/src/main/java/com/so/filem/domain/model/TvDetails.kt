package com.so.filem.domain.model

data class TvDetails(
    val tvShow: TvShow,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val trailers: List<Trailer>
)
