package com.so.filem.domain.model

data class Casts(
    val page: Int,
    val results: List<Cast>,
    val total_pages: Int,
    val total_results: Int
)