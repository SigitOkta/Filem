package com.so.filem.data.remote.response

import com.so.filem.domain.model.movie.Movie

data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int)
