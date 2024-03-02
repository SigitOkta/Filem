/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.remote.response

data class TvResponse(
    val page: Int,
    val results: List<TvListResponse>,
    val total_pages: Int,
    val total_results: Int
)
