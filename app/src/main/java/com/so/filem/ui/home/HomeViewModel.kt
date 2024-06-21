/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.R
import com.so.filem.data.firebase.User
import com.so.filem.data.repository.UserRepository
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import com.so.filem.domain.usecase.movie.GetDiscoverMovieUseCase
import com.so.filem.domain.usecase.tv.GetDiscoverTvUseCase
import com.so.filem.domain.usecase.movie.GetPopularPeopleUseCase
import com.so.filem.domain.usecase.movie.GetTrendingMovieUseCase
import com.so.filem.domain.usecase.tv.GetTrendingTvUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase,
    private val getTrendingTvUseCase: GetTrendingTvUseCase,
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase,
    private val getDiscoverTvUseCase: GetDiscoverTvUseCase,
    private val getPopularPeopleUseCase: GetPopularPeopleUseCase,
    private val userRepository: UserRepository
) : ViewModel() {

    private var time: String = "day"

    private val _trendingMovie = MutableLiveData<Movies>()
    val trendingMovie: LiveData<Movies> = _trendingMovie

    private val _trendingTv = MutableLiveData<Tvs>()
    val trendingTv: LiveData<Tvs> = _trendingTv

    private val _parentData = MutableLiveData<Resource<List<HomeItem>>>()
    val parentData: LiveData<Resource<List<HomeItem>>> = _parentData

    val currentUserLiveData = MutableLiveData<User?>()

    fun getCurrentUser() {
        currentUserLiveData.postValue(userRepository.getCurrentUser())
    }
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
            _parentData.postValue(Resource.Loading())
            try {
                val titleTabTrendingMovie = listOf("day", "week")
                val titleTabTrendingTv = listOf("day", "week")
                val movie = getDiscoverMovieUseCase()
                val tv = getDiscoverTvUseCase()
                val people = getPopularPeopleUseCase()
                val homeItems = mutableListOf<HomeItem>()
                homeItems.apply {
                    add(HomeItem.HomeTrendingMovieItem(R.drawable.ic_trending,
                        R.string.en_text_trending_movies, "movie", titleTabTrendingMovie))
                    add(HomeItem.HomeHeaderMovieItem(movie.random()))
                    add(HomeItem.HomeTrendingTvShowItem(R.drawable.ic_tv_off_white,
                        R.string.en_text_trending_tvs, "tv", titleTabTrendingTv))
                    add(HomeItem.HomeHeaderTvShowItem(tv.random()))
                    add(HomeItem.HomePopularPeopleItem(R.drawable.ic_people,
                        R.string.en_text_popular_people, people))
                }
                if (homeItems.isNotEmpty()) {
                    _parentData.postValue(Resource.Success(homeItems))
                } else {
                    _parentData.postValue(Resource.Empty())
                }

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}