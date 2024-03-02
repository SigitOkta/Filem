/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.filter.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _movies = MutableStateFlow<PagingData<MoviePaging>?>(null)
    private var currentFilter: MovieFilter? = null
    val movies: Flow<PagingData<MoviePaging>?>
        get() = _movies

    fun setFilter(filter: MovieFilter) {
        currentFilter = filter
        getFilterMovie()
    }


    private fun getFilterMovie() {
        val filter = currentFilter ?: return
        viewModelScope.launch {
            getMovieUseCase.getFilterMovieUseCase(filter)
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _movies.value = it
                }
        }
    }

    fun getFilterTitle(filter: MovieFilter): String {
        return when (filter) {
            MovieFilter.NOW_PLAYING -> "Now Playing"
            MovieFilter.UPCOMING -> "Up Coming"
            MovieFilter.POPULAR -> "Popular"
            MovieFilter.TOP_RATED -> "Top Rated"
        }
    }
    /*private val _movieResult = MutableStateFlow<Resource<PagingData<MoviePaging>>?>(null)
        val movieResult: StateFlow<Resource<PagingData<MoviePaging>>?> = _movieResult

        fun getMovieList(filter: MovieFilter) {
            viewModelScope.launch {
                getFilteredMovies(filter).collect {
                    _movieResult.value = it
                }
            }
        }

        private suspend fun getFilteredMovies(filter: MovieFilter): Flow<Resource<PagingData<MoviePaging>>> {
            return flow {
                emit(Resource.Loading())
                try {
                    getMovieUseCase.getFilterMovieUseCase.invoke(filter).cachedIn(viewModelScope)
                        .collect {
                            emit(Resource.Success(it))
                        }
                } catch (e: Exception) {
                    emit(Resource.Error(e))
                }
            }
        }

        }*/
}
