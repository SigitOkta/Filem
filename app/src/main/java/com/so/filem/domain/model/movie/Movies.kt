package com.so.filem.domain.model.movie

import com.so.filem.data.remote.response.MoviesListResponse

data class Movies(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)