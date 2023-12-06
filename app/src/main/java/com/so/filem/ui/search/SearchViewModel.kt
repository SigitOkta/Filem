package com.so.filem.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.so.filem.domain.model.Search
import com.so.filem.domain.usecase.movie.GetSearchMultiUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMultiUseCase: GetSearchMultiUseCase,
) : ViewModel() {

    private val _searchResults = MutableStateFlow<Resource<PagingData<Search>>?>(null)
    val searchResults: StateFlow<Resource<PagingData<Search>>?> = _searchResults
    fun fetchSearch(query: String, spinner: String) {
        viewModelScope.launch {
            search(query, spinner.trim().lowercase()).collect {
                if (query.isNotBlank()) _searchResults.value = it else null
                Timber.tag("viewModel-getSearch").d( _searchResults.value.toString())
            }

        }
       Timber.tag("viewModel-query,spinner").d("$query,$spinner")
    }

    private suspend fun search(query: String, spinnerValue: String): Flow<Resource<PagingData<Search>>> {
        return flow {
            emit(Resource.Loading())
            try {
                getSearchMultiUseCase.invoke(query).cachedIn(viewModelScope)
                    .collect { search ->
                        when(spinnerValue){
                            "movie" -> {
                                val movieItem = search.filter { it.mediaType == "movie" }
                                emit(Resource.Success(movieItem))
                            }
                            "tv" -> {
                                val tvItem = search.filter { it.mediaType == "tv" }
                                emit(Resource.Success(tvItem))
                            }
                            "person" -> {
                                val personItem = search.filter { it.mediaType == "person" }
                                emit(Resource.Success(personItem))
                            }
                        }
                    }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }


    /*fun updateQuery(query: String, spinnerValue: String) {
        _searchQuery.value = query
        selectedValue.value = spinnerValue
        Timber.tag("viewModel-query").d(_searchQuery.value)
    }*/
}