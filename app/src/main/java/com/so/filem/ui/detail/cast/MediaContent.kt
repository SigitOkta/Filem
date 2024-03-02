/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.cast

import com.so.filem.domain.model.MediaItem



data class MediaContent(
    val image : Int ,
    val title : String,
    val childItemList : List<MediaItem>,
    var isOpen : Boolean = false
)
