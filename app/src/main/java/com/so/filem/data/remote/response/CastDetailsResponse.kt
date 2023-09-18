package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class CastDetailsResponse(

    @SerializedName("also_known_as")
    val alsoKnownAs: List<String>,

    @SerializedName("birthday")
    val birthday: String?,

    @SerializedName("images")
    val imagesResponse: ImagesCastResponse,

    @SerializedName("gender")
    val gender: Int?,

    @SerializedName("known_for_department")
    val knownForDepartment: String?,

    @SerializedName("profile_path")
    val profilePath: String?,

    @SerializedName("biography")
    val biography: String?,

    @SerializedName("deathday")
    val deathDay: String?,

    @SerializedName("place_of_birth")
    val placeOfBirth: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("combined_credits")
    val combinedCreditsResponse: CombinedCreditsResponse,

    @SerializedName("id")
    val id: Int,

    @SerializedName("adult")
    val adult: Boolean?,
)

data class CombinedCreditsResponse(
    @SerializedName("cast")
    val cast: List<MediaItemResponse>?
)

data class ImagesCastResponse(

    @SerializedName("profiles")
    val profiles: List<ProfilesItemResponse>?
)

data class MediaItemResponse(

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("media_type")
    val mediaType: String?,

    @SerializedName("name")
    val name: String?,

    @SerializedName("id")
    val id: Int,

    @SerializedName("adult")
    val adult: Boolean?,

    @SerializedName("title")
    val title: String?,

    @SerializedName("character")
    val character: String?,
)

data class ProfilesItemResponse(
    @SerializedName("file_path")
    val filePath: String?,
)
