/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.search

import androidx.paging.PagingData
import com.so.filem.domain.model.Search

data class SearchItem(
    val image : Int,
    val title: String,
    val searchList: PagingData<Search>,
    var isOpen : Boolean = false,
)