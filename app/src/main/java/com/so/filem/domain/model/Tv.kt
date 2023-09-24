package com.so.filem.domain.model

import com.so.filem.domain.utils.Constants

data class Tv(
    val id: Long,
    val adult: Boolean?,
    val backdropPath: String?,
    val original_language: String?,
    val original_name: String?,
    val overview: String?,
    val posterPath: String?,
    val first_air_date: String?,
    val name: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val isFavorite: Boolean?

) {
    val posterUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
    val backdropUrl = if (backdropPath != null) Constants.BACKDROP_URL + backdropPath else null
}