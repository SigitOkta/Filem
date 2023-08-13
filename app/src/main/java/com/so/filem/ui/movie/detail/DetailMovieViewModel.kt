package com.so.filem.ui.movie.detail

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieUseCase: GetMovieUseCase,
) : ViewModel() {

    private val _selectedMovie = MutableStateFlow<Resource<Movie>?>(null)
    val selectedMovie: StateFlow<Resource<Movie>?> = _selectedMovie

    fun getMovieDetails(id : Int) {
        viewModelScope.launch {
            getDetailsMovie(movieID = id).collect {
                _selectedMovie.value = it
            }
        }
    }

    private suspend fun getDetailsMovie(movieID: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading())
            try {
                getMovieUseCase.getMovieFromDBUseCase.invoke(movieID = movieID)
                    .collect {
                        emit(Resource.Success(it))
                    }
                Timber.tag("viewModel").d(movieID.toString())
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}