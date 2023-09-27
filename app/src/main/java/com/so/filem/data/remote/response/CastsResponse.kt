package com.so.filem.data.remote.response

data class CastsResponse(
    val page: Int,
    val results: List<CastResponse>,
    val total_pages: Int,
    val total_results: Int
)
