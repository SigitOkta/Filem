package com.so.filem.domain.model

import com.so.filem.domain.utils.Constants

data class CastDetails(
    val alsoKnownAs: List<String>,
    val birthday: String?,
    val gender: Int?,
    val knownForDepartment: String?,
    val profilePath: String?,
    val biography: String?,
    val deathDay: String?,
    val placeOfBirth: String?,
    val name: String?,
    val id: Int,
    val adult: Boolean?,
    val images: List<ProfilesItem>,
    val combinedCredits: List<MediaItem>
)

data class CombinedCredits(
    val cast: List<MediaItem>?
)

data class ImagesCast(
    val profiles: List<ProfilesItem>?
)

data class MediaItem(
    val posterPath: String?,
    val mediaType: String?,
    val name: String?,
    val id: Int,
    val adult: Boolean?,
    val title: String?,
    val character: String?,
)

data class ProfilesItem(
    val filePath: String?,
){
    val profileImageUrl = if (filePath != null) Constants.CAST_AVATAR_URL + filePath else null
}