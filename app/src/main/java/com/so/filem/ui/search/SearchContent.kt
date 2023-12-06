package com.so.filem.ui.search

import androidx.paging.PagingData
import com.so.filem.domain.model.Search

data class SearchItem(
    val image : Int,
    val title: String,
    val searchList: PagingData<Search>,
    var isOpen : Boolean = false,
)