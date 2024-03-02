/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

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
