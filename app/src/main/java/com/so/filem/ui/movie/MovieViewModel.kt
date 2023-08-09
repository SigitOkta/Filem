package com.so.filem.ui.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.usecase.movie.GetFilteredMovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getFilteredMoviesUseCase: GetFilteredMovieUseCase,
) : ViewModel() {

    /*private var currentFilter: MovieFilter = MovieFilter.NOW_PLAYING*/

    fun getFilteredMovies(filter: MovieFilter): Flow<PagingData<Movie>> {
        return getFilteredMoviesUseCase(filter).cachedIn(viewModelScope)
    }

   /* fun setFilter(filter: MovieFilter) {
        currentFilter = filter
        Timber.tag("viewModel").d(currentFilter.toString())
    }*/

    fun getFilterTitle(filter: MovieFilter): String {
        return when (filter) {
            MovieFilter.NOW_PLAYING -> "Now Playing"
            MovieFilter.UPCOMING -> "Up Coming"
        }
    }
}