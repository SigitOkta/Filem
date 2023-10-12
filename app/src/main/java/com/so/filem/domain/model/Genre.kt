package com.so.filem.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Genre(
    val id: Long,
    val movieId: Long,
    val name: String?
) : Parcelable
