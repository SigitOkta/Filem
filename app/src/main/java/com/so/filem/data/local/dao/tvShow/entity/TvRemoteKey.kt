package com.so.filem.data.local.dao.tvShow.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tv_remote_key")
data class TvRemoteKey(
    @PrimaryKey
    val id: Long,
    val prevPage: Int?,
    val nextPage: Int?,
    val lastUpdated: Long?,
)
