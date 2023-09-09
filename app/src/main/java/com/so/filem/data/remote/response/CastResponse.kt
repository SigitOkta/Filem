package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class CastResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val actorName: String?,
    @SerializedName("profile_path") val profileImagePath: String?
)
