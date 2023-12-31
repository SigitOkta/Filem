package com.so.filem.data.firebase

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class ThreadItem(
    var id: String = "",
    val idMediaType: String = "",
    val creator: User? = null,
    val title: String = "",
    val content: String = "",
    var members: MutableList<User?> = mutableListOf(),
) : Parcelable