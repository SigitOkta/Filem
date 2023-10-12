package com.so.filem.domain.model

import android.os.Parcelable
import com.so.filem.domain.utils.Constants.BASE_URL
import com.so.filem.domain.utils.Constants.CAST_AVATAR_URL
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cast(
    val id: Long,
    val movieId: Long,
    val actorName: String?,
    val profileImagePath: String?
) : Parcelable {

    @IgnoredOnParcel
    val profileImageUrl = if (profileImagePath != null) CAST_AVATAR_URL + profileImagePath else null
}
