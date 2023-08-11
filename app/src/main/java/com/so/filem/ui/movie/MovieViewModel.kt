package com.so.filem.ui.movie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.usecase.movie.GetFilteredMovieUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getFilteredMoviesUseCase: GetFilteredMovieUseCase,
) : ViewModel() {

    private val _MovieResult = MutableLiveData<Resource<PagingData<Movie>>>()
    val getMovieResult get() = _MovieResult

    fun getMovieList(filter: MovieFilter) {
        viewModelScope.launch {
            getFilteredMovies(filter).collect {
                _MovieResult.postValue(it)
            }
        }
    }

    private suspend fun getFilteredMovies(filter: MovieFilter): Flow<Resource<PagingData<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                getFilteredMoviesUseCase(filter).cachedIn(viewModelScope)
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

/* fun setFilter(filter: MovieFilter) {
     currentFilter = filter
     Timber.tag("viewModel").d(currentFilter.toString())
 }*/

