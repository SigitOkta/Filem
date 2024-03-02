/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.remote.response

data class MoviesResponse(
    val page: Int,
    val results: List<MoviesListResponse>,
    val total_pages: Int,
    val total_results: Int
)
