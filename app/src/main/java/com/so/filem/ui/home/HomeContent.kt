/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.home

import androidx.annotation.StringRes
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.TvShow


/*data class HomeContent(
    val image : Int,
    val title : String,
    val mediaType : String,
    val titleTab : List<String>,
    *//*val childItemList : List<Movie>*//*
)*/

const val HOME_TYPE_HEADER_MOVIE = 0
const val HOME_TYPE_TRENDING_MOVIE = 1
const val HOME_TYPE_HEADER_TV = 2
const val HOME_TYPE_TRENDING_TV = 3
const val HOME_TYPE_POPULAR_PEOPLE = 4

sealed class HomeItem(val type: Int) {
    class HomeHeaderMovieItem(val data: Movie) : HomeItem(HOME_TYPE_HEADER_MOVIE)
    class HomeTrendingMovieItem(
        val image : Int,
        @StringRes val title : Int,
        val mediaType : String,
        val titleTab : List<String>,
    ) :
        HomeItem(HOME_TYPE_TRENDING_MOVIE)

    class HomeHeaderTvShowItem(val data: TvShow) : HomeItem(HOME_TYPE_HEADER_TV)
    class HomeTrendingTvShowItem(
        val image : Int,
        @StringRes val title : Int,
        val mediaType : String,
        val titleTab : List<String>,
    ) :
        HomeItem(HOME_TYPE_TRENDING_TV)

    class HomePopularPeopleItem(
        val image : Int,
        @StringRes val title : Int,
        val castList : List<Cast>,
    ) :
        HomeItem(HOME_TYPE_POPULAR_PEOPLE)
}

