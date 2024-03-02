/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.remote.response

data class TvListResponse(
    val id: Long,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val poster_path: String?,
    val first_air_date: String,
    val name: String?,
    val vote_average: Double?,
    val vote_count: Int?,
)
