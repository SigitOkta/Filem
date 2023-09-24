package com.so.filem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
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
    private val getTrendingTvUseCase: GetTrendingTvUseCase
) : ViewModel() {

    private var time: String = "day"

    private val _trendingMovie = MutableLiveData<Movies>()
    val trendingMovie: LiveData<Movies> = _trendingMovie

    private val _trendingTv = MutableLiveData<Tvs>()
    val trendingTv: LiveData<Tvs> = _trendingTv

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
}