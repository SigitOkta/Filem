package com.so.filem.data.local.dao.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class MoviesEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
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
    val vote_count: Int?,
    @field:SerializedName("is_favorite")
    val isFavorite: Boolean
)