package com.so.filem.data.firebase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    var id: String = "",
    val displayName: String = "",
    val email: String = "",
    val photoProfileUrl: String = "",
): Parcelable

