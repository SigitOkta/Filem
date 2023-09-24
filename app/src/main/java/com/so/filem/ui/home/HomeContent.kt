package com.so.filem.ui.home

import com.so.filem.domain.model.Movie


data class HomeContent(
    val image : Int,
    val title : String,
    val titleTab : List<String>,
    /*val childItemList : List<Movie>*/
)
