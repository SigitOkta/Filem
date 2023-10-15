package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchMultiResponse(

	@field:SerializedName("page")
	val page: Int,

	@field:SerializedName("total_pages")
	val totalPages: Int,

	@field:SerializedName("results")
	val results: List<SearchResponse>,

	@field:SerializedName("total_results")
	val totalResults: Int
)