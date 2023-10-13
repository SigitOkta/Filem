package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class SeasonsResponse(

	@field:SerializedName("seasons")
	val seasons: List<SeasonResponse>?
)