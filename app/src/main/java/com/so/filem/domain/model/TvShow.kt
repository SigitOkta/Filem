/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.model

import android.os.Parcelable
import com.so.filem.domain.utils.Constants
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvShow(
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

) : Parcelable {
    @IgnoredOnParcel
    val posterUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
    @IgnoredOnParcel
    val backdropUrl = if (backdropPath != null) Constants.BACKDROP_URL + backdropPath else null
}