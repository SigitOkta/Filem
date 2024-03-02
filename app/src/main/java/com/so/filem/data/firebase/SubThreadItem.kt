/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.firebase

import androidx.annotation.Keep

@Keep
data class SubThreadItem(
    var id : String? = "",
    val sender: User? = null,
    val message: String = ""
)