package com.so.filem.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.usecase.movie.GetMovieDetailsUseCase
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
) : ViewModel() {

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>?>(null)
    val movieDetails: StateFlow<Resource<MovieDetails>?> = _movieDetails

    fun getMovieId(id: Long) {
        getMovieDetails(id)
        Timber.tag("viewModel-id").d(id.toString())
    }

    private fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading()
            try {
                val details = getMovieDetailsUseCase(movieId)
                _movieDetails.value = Resource.Success(details)
                Timber.tag("viewModel").d(details.movie.id.toString())
            } catch (e: Exception) {
                Timber.tag("viewModel").e(e)
                _movieDetails.value = Resource.Error(e)
            }
        }
    }
}