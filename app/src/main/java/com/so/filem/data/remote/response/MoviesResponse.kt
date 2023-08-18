package com.so.filem.data.remote.response

data class MoviesResponse(
    val page: Int,
    val results: List<MoviesListResponse>,
    val total_pages: Int,
    val total_results: Int
)
