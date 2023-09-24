package com.so.filem.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.usecase.movie.GetTrendingMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getTrendingMovieUseCase: GetTrendingMovieUseCase
) : ViewModel() {

    private var time: String = "day"

    private val _trendingMovie = MutableLiveData<Movies>()
    val trendingMovie: LiveData<Movies> = _trendingMovie

    fun setTrending(timeWindow: String) {
        time = timeWindow
        getTrendingMovie()
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