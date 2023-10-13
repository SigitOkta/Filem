package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class TvDetailsResponse(
    val id: Long,
    val adult: Boolean?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_name") val originalName: String?,
    val overview: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    val name: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    val genres: List<GenreResponse>?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("credits") val creditsResponse: CreditsResponse,
    @SerializedName("videos") val videosResponse: VideosResponse,
    @SerializedName("seasons") val seasonsResponse: List<SeasonResponse>?,
)
