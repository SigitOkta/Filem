package com.so.filem.ui.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.Movie
import com.so.filem.domain.usecase.movie.GetAllFavoriteMoviesUseCase
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
) : ViewModel() {

    private val _movies = MutableStateFlow<List<Movie>?>(null)

    val movies: Flow<List<Movie>?>
        get() = _movies

    init {
        getFavoriteMovies()
    }

    private fun getFavoriteMovies() {
        viewModelScope.launch {
            getAllFavoriteMoviesUseCase()
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _movies.value = it
                }
        }
    }
}