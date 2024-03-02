/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.model

import com.so.filem.domain.utils.Constants

data class Movie(
    val id: Long,
    val adult: Boolean?,
    val backdropPath: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val release_date: String?,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val runtime: Int?,
    val isFavorite: Boolean?

) {
    val posterUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
    val backdropUrl = if (backdropPath != null) Constants.BACKDROP_URL + backdropPath else null
}