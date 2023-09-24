package com.so.filem.ui.home

import androidx.annotation.StringRes
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.Tv


data class HomeContent(
    val image : Int,
    val title : String,
    val mediaType : String,
    val titleTab : List<String>,
    /*val childItemList : List<Movie>*/
)

const val HOME_TYPE_HEADER = 0
const val HOME_TYPE_SECTION = 1


sealed class HomeItem(val type: Int) {
    class HomeHeaderMovieItem(val data: Movie) : HomeItem(HOME_TYPE_HEADER)
    class HomeSectionMovieItem(val data: List<Movie>) :
        HomeItem(HOME_TYPE_SECTION)

    class HomeHeaderTvShowItem(val data: Tv) : HomeItem(HOME_TYPE_HEADER)
    class HomeSectionTvShowItem(val data: List<Tv>) :
        HomeItem(HOME_TYPE_SECTION)
}

