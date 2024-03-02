/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.local.dao.tvShow.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvShows")
data class TvsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @field:SerializedName("adult")
    val adult: Boolean?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("original_language")
    val original_language: String?,
    @field:SerializedName("original_name")
    val original_name: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("first_air_date")
    val first_air_date: String?,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?,
    @field:SerializedName("is_favorite")
    val isFavorite: Boolean
)