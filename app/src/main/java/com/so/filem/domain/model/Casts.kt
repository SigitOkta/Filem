/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.model

data class Casts(
    val page: Int,
    val results: List<Cast>,
    val total_pages: Int,
    val total_results: Int
)