package com.so.filem.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.so.filem.domain.model.Search
import com.so.filem.domain.usecase.movie.GetSearchMultiUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.switchMap
import kotlinx.coroutines.flow.transformLatest
import timber.log.Timber
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMultiUseCase: GetSearchMultiUseCase,
) : ViewModel() {

    private val _searchQuery : MutableLiveData<String> = MutableLiveData()

    private val _type = MutableLiveData<Int>()

    init {
        _type.value = 0
    }

    val movies = _searchQuery
        .switchMap { query ->
            _type.switchMap { type ->
                getSearchMultiUseCase.invoke(query, type).cachedIn(viewModelScope)
            }
        }

    fun searchMovies(searchQuery: String, type: Int) {
        _searchQuery.value = searchQuery
        _type.value = type
    }

    /*private val _searchResults = MutableStateFlow<Resource<PagingData<Search>>?>(null)
    val searchResults: StateFlow<Resource<PagingData<Search>>?> = _searchResults
    fun fetchSearch(query: String, spinner: String) {
        viewModelScope.launch {
            search(query, spinner.trim().lowercase()).collectLatest {
                if (query.isNotBlank()) _searchResults.value = it
                Timber.tag("viewModel-getSearch").d( _searchResults.value.toString())
            }
        }
       Timber.tag("viewModel-query,spinner").d("$query,$spinner")
    }

    private suspend fun search(query: String, spinnerValue: String): Flow<Resource<PagingData<Search>>> {
        return flow {
            _searchResults.value = Resource.Loading()
            delay(2000)
            try {
                getSearchMultiUseCase.invoke(query).cachedIn(viewModelScope)
                    .collectLatest { search ->
                        when(spinnerValue){
                            "movie" -> {
                                val movieItem = search.filter { it.mediaType == "movie" }
                                _searchResults.value = Resource.Success(movieItem)
                            }
                            "tv" -> {
                                val tvItem = search.filter { it.mediaType == "tv" }
                                _searchResults.value = Resource.Success(tvItem)
                            }
                            "person" -> {
                                val personItem = search.filter { it.mediaType == "person" }
                                _searchResults.value = Resource.Success(personItem)
                            }
                        }
                    }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }*/
}