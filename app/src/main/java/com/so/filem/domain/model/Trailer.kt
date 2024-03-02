/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

private const val YOUTUBE_APP_BASE_URL = "vnd.youtube:"
private const val YOUTUBE_WEB_BASE_URL = "https://www.youtube.com/watch?v="
private const val YOUTUBE_TRAILER_THUMBNAIL_BASE_URL = "https://img.youtube.com/vi/"
private const val YOUTUBE_TRAILER_THUMBNAIL_HQ_SUFFIX = "/hqdefault.jpg"

@Parcelize
data class Trailer(
    val id: String,
    val movieId: Long,
    val key: String?,
    val name: String?
) : Parcelable {
    @IgnoredOnParcel
    val youTubeThumbnailUrl = if (key != null) {
        YOUTUBE_TRAILER_THUMBNAIL_BASE_URL + key + YOUTUBE_TRAILER_THUMBNAIL_HQ_SUFFIX
    } else null

    @IgnoredOnParcel
    val youTubeAppUrl = if (key != null) YOUTUBE_APP_BASE_URL + key else null
    @IgnoredOnParcel
    val youTubeWebUrl = if (key != null) YOUTUBE_WEB_BASE_URL + key else null
}