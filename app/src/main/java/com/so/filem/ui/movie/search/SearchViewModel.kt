package com.so.filem.ui.movie.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.usecase.movie.GetDiscoverMovieUseCase
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import com.so.filem.domain.usecase.movie.GetSearchMoviesUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMoviesUseCase: GetSearchMoviesUseCase,
    private val getDiscoverMovieUseCase: GetDiscoverMovieUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<Resource<PagingData<Movie>>?>(null)
    val searchResults: StateFlow<Resource<PagingData<Movie>>?> = _searchResults

    private val _discoverMovie = MutableStateFlow<List<Movie>>(emptyList())
    val discoverMovie: StateFlow<List<Movie>> = _discoverMovie

    private var currentJob: Job? = null

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce(300)
                .collectLatest { query ->
                    currentJob?.cancel()
                    currentJob = launch {
                        Timber.tag("viewModel-init").d(_searchQuery.value)
                        Timber.tag("viewModel-init-query").d(query)
                        if (query.isNotBlank()) getSearch(query.trim()) else null
                    }
                }

        }
        getDiscoverMovie()
    }

    private fun getDiscoverMovie() {
        viewModelScope.launch {
            try {
                val remote = getDiscoverMovieUseCase.invoke()
                _discoverMovie.value = remote
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    private fun getSearch(query: String) {
        viewModelScope.launch {
            search(query).collect {
                _searchResults.value = it
                Timber.tag("viewModel-getSearch").d( _searchResults.value.toString())
            }
        }
    }

    private suspend fun search(query: String): Flow<Resource<PagingData<Movie>>> {
        return flow {
            emit(Resource.Loading())
            try {
                getSearchMoviesUseCase.invoke(query).cachedIn(viewModelScope)
                    .collect {
                        emit(Resource.Success(it))
                        Timber.tag("viewModel-search").d( it.toString())
                    }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }


    fun updateQuery(query: String) {
        _searchQuery.value = query
        Timber.tag("viewModel-query").d(_searchQuery.value)
    }
}