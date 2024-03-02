/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenreResponse(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String?
)
