package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("id") val id: String,
    @SerializedName("key") val key: String?,
    @SerializedName("name") val name: String?
)
