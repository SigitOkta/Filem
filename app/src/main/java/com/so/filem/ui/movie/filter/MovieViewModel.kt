package com.so.filem.ui.movie.filter

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _movieResult = MutableStateFlow<Resource<PagingData<MoviePaging>>?>(null)
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
    fun getFilterTitle(filter: MovieFilter): String {
        return when (filter) {
            MovieFilter.NOW_PLAYING -> "Now Playing"
            MovieFilter.UPCOMING -> "Up Coming"
            MovieFilter.POPULAR -> "Popular"
            MovieFilter.TOP_RATED -> "Top Rated"
        }
    }
}
