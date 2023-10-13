package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class SeasonResponse(

	@SerializedName("air_date")
	val airDate: String,

	@SerializedName("overview")
	val overview: String,

	@SerializedName("episode_count")
	val episodeCount: Int,

	@SerializedName("vote_average")
	val voteAverage: Double,

	@SerializedName("name")
	val name: String,

	@SerializedName("season_number")
	val seasonNumber: Int,

	@SerializedName("id")
	val id: Int,

	@SerializedName("poster_path")
	val posterPath: String
)