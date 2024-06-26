/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.local.dao.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies_paging")
data class MoviePaging(
    @PrimaryKey(autoGenerate = true)
    val pk: Long = 0,
    @field:SerializedName("id")
    val id: Long,
    @field:SerializedName("adult")
    val adult: Boolean?,
    @field:SerializedName("backdrop_path")
    val backdrop_path: String?,
    @field:SerializedName("original_language")
    val original_language: String?,
    @field:SerializedName("original_title")
    val original_title: String?,
    @field:SerializedName("overview")
    val overview: String?,
    @field:SerializedName("popularity")
    val popularity: Double?,
    @field:SerializedName("poster_path")
    val poster_path: String?,
    @field:SerializedName("release_date")
    val release_date: String?,
    @field:SerializedName("title")
    val title: String?,
    @field:SerializedName("vote_average")
    val vote_average: Double?,
    @field:SerializedName("vote_count")
    val vote_count: Int?
) {

}
