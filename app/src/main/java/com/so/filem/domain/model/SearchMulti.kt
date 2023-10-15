package com.so.filem.domain.model

data class SearchMulti(
    val page: Int,
    val results: List<Search>,
    val totalPages: Int,
    val totalResults: Int
)