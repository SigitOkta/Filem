package com.so.filem.ui.home

import androidx.annotation.StringRes
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.Tv


/*data class HomeContent(
    val image : Int,
    val title : String,
    val mediaType : String,
    val titleTab : List<String>,
    *//*val childItemList : List<Movie>*//*
)*/

const val HOME_TYPE_HEADER_MOVIE = 0
const val HOME_TYPE_TRENDING_MOVIE = 1
const val HOME_TYPE_TRENDING_TV = 2
sealed class HomeItem(val type: Int) {
    class HomeHeaderMovieItem(val data: Movie) : HomeItem(HOME_TYPE_HEADER_MOVIE)
    class HomeTrendingMovieItem(
        val image : Int,
        @StringRes val title : Int,
        val mediaType : String,
        val titleTab : List<String>,
    ) :
        HomeItem(HOME_TYPE_TRENDING_MOVIE)

    class HomeHeaderTvShowItem(val data: Tv) : HomeItem(HOME_TYPE_HEADER_MOVIE)
    class HomeTrendingTvShowItem(
        val image : Int,
        @StringRes val title : Int,
        val mediaType : String,
        val titleTab : List<String>,
    ) :
        HomeItem(HOME_TYPE_TRENDING_TV)
}

