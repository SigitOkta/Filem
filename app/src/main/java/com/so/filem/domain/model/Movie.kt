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
    val isFavorite: Boolean?

) {
    val posterUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
    val backdropUrl = if (backdropPath != null) Constants.BACKDROP_URL + backdropPath else null
}