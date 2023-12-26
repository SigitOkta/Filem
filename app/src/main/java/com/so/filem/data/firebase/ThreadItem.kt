package com.so.filem.data.firebase

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ThreadItem(
    var id: String = "",
    val movieId: String = "",
    val creator: User? = null,
    val title: String = "",
    val content: String = "",
    var members: MutableList<User?> = mutableListOf(),
    val isMember: Boolean = false
) : Parcelable