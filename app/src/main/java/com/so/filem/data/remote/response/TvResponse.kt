package com.so.filem.data.remote.response

data class TvResponse(
    val page: Int,
    val results: List<TvListResponse>,
    val total_pages: Int,
    val total_results: Int
)
