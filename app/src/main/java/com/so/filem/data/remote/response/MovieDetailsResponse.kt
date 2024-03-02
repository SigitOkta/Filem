/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

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
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("credits") val creditsResponse: CreditsResponse,
    @SerializedName("videos") val videosResponse: VideosResponse,
)
