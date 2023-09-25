package com.so.filem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.R
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import com.so.filem.domain.usecase.movie.GetDiscoverMovieUseCase
import com.so.filem.domain.usecase.movie.GetTrendingMovieUseCase
import com.so.filem.domain.usecase.movie.GetTrendingTvUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val getTrendingTvUseCase: GetTrendingTvUseCase,
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase
) : ViewModel() {

    private var time: String = "day"

    private val _trendingMovie = MutableLiveData<Movies>()
    val trendingMovie: LiveData<Movies> = _trendingMovie

    private val _trendingTv = MutableLiveData<Tvs>()
    val trendingTv: LiveData<Tvs> = _trendingTv

    private val _parentData = MutableLiveData<List<HomeItem>>()
    val parentData: LiveData<List<HomeItem>> = _parentData

    fun setTrendingMovie(timeWindow: String) {
        time = timeWindow
        getTrendingMovie()
    }

    fun setTrendingTv(timeWindow: String) {
        time = timeWindow
        getTrendingTv()
    }

    private fun getTrendingTv() {
        viewModelScope.launch {
            try {
                val result = getTrendingTvUseCase(time)
                _trendingTv.value = result
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun getTrendingMovie() {
        viewModelScope.launch {
            try {
                val result = getTrendingMovieUseCase(time)
                _trendingMovie.value = result
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    fun getParentData() {
        viewModelScope.launch {
            try {
                val titleTabTrendingMovie = listOf("day", "week")
                val titleTabTrendingTv = listOf("day", "week")
                val movie = getDiscoverMovieUseCase()
                val homeItems = mutableListOf<HomeItem>()
                homeItems.apply {
                    add(HomeItem.HomeTrendingMovieItem(R.drawable.ic_trending,
                        R.string.en_text_trending_movies, "movie", titleTabTrendingMovie))
                    add(HomeItem.HomeHeaderMovieItem(movie.random()))
                    add(HomeItem.HomeTrendingTvShowItem(R.drawable.ic_tv_off_white,
                        R.string.en_text_trending_tvs, "tv", titleTabTrendingTv))
                    add(HomeItem.HomeHeaderMovieItem(movie.random()))
                }
                _parentData.value = homeItems
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}