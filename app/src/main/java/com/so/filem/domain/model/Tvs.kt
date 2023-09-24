package com.so.filem.domain.model

data class Tvs (
    val page: Int,
    val results: List<Tv>,
    val total_pages: Int,
    val total_results: Int
)