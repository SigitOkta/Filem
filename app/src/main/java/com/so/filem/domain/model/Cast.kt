package com.so.filem.domain.model

import com.so.filem.domain.utils.Constants.BASE_URL
import com.so.filem.domain.utils.Constants.CAST_AVATAR_URL

data class Cast(
    val id: Long,
    val movieId: Long,
    val actorName: String?,
    val profileImagePath: String?
) {
    val profileImageUrl = if (profileImagePath != null) CAST_AVATAR_URL + profileImagePath else null
}
