package com.so.filem.data.remote.response

data class MovieDetailsResponse(
    val id: Long,
    val adult: Boolean?,
    val backdrop_path: String?,
    val original_language: String?,
    val original_title: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val release_date: String,
    val title: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val genres: List<GenreResponse>?,
    val creditsResponse: CreditsResponse,
    val videosResponse: VideosResponse,
)
