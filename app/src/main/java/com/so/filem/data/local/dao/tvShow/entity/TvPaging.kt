/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.local.dao.tvShow.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tv_paging")
data class TvPaging(
    @PrimaryKey(autoGenerate = true)
    val pk: Long = 0,
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("adult")
    val adult: Boolean?,
    @field:SerializedName("backdrop_path")
    val backdropPath: String?,
    @field:SerializedName("original_language")
    val originalLanguage: String?,
    @field:SerializedName("original_name")
    val originalName: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poster_path")
    val posterPath: String?,
    @field:SerializedName("first_air_date")
    val firstAirDate: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("vote_average")
    val voteAverage: Double?,
    @field:SerializedName("vote_count")
    val voteCount: Int?
)
