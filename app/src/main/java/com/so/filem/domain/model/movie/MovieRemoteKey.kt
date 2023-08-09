package com.so.filem.domain.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_key")
data class MovieRemoteKey(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)
