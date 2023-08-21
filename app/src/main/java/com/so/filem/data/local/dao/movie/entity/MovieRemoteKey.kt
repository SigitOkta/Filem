package com.so.filem.data.local.dao.movie.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_remote_key")
data class MovieRemoteKey(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)
