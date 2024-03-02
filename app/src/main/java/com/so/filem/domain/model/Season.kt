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
data class Season(
    val id: Int,
    val airDate: String?,
    val episodeCount: Int,
    val name: String?,
    val overview: String?,
    val posterPath: String?,
    val seasonNumber: Int,
    val voteAverage: Double
) : Parcelable {

    @IgnoredOnParcel
    val profileImageUrl = if (posterPath != null) Constants.POSTER_URL + posterPath else null
}