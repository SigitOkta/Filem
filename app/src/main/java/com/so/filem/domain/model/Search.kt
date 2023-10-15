package com.so.filem.domain.model

import com.so.filem.domain.utils.Constants

data class Search(
    val id: Int,
    val adult: Boolean?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val title: String?,
    val posterPath: String?,
    val mediaType: String?,
    val originalName: String?,
    val name: String?,
    val profilePath: String?,

) {
    val posterUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
    val profileUrl = if (profilePath != null) Constants.CAST_AVATAR_URL + profilePath else null
}