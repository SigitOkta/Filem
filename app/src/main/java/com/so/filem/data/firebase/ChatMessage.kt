package com.so.filem.data.firebase

import androidx.annotation.Keep

@Keep
data class ChatMessage(
    var id : String = "",
    val sender: User? = null,
    val message: String = ""
)
