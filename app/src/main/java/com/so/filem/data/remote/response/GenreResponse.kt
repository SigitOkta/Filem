package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?
)
