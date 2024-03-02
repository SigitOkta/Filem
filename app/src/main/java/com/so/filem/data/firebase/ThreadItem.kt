/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

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
) : Parcelable