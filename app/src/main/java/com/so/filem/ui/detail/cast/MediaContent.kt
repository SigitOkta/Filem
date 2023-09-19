package com.so.filem.ui.detail.cast

import com.so.filem.domain.model.MediaItem

data class MediaContent(
    val image : Int ,
    val title : String,
    val childItemList : List<MediaItem>,
    var isOpen : Boolean = false
)
