/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.TvShow
import com.so.filem.domain.usecase.movie.GetAllFavoriteMoviesUseCase
import com.so.filem.domain.usecase.movie.GetAllFavoriteTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoriteMoviesUseCase: GetAllFavoriteMoviesUseCase,
    private val getAllFavoriteTvUseCase: GetAllFavoriteTvUseCase,
) : ViewModel() {

    private var currentFilter: String? = null
    private val _movies = MutableStateFlow<List<Movie>?>(null)
    private val _tvs = MutableStateFlow<List<TvShow>?>(null)

    val movies: Flow<List<Movie>?>
        get() = _movies

    val tvs: Flow<List<TvShow>?>
        get() = _tvs

    fun setFilter(filter: String) {
        currentFilter = filter
        Timber.tag("viewModelFav").d(filter)
        if (filter == "Favorite Movie"){
            getFavoriteMovies()
        } else {
            getFavoriteTvs()
        }
    }

    private fun getFavoriteTvs() {
        viewModelScope.launch {
            getAllFavoriteTvUseCase()
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _tvs.value = it
                    Timber.tag("viewModelFavTv").d(it.toString())
                }
        }
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            getAllFavoriteMoviesUseCase()
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _movies.value = it
                    Timber.tag("viewModelFavMovie").d(it.toString())
                }
        }
    }
}