package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class CreditsResponse(
    @SerializedName("cast") val cast: List<CastResponse>?
)
