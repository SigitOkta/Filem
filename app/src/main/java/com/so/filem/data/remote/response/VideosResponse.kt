package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class VideosResponse(
    @SerializedName("results") val videos: List<VideoResponse>?
)
