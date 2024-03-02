/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.so.filem.domain.usecase.movie.GetSearchMultiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getSearchMultiUseCase: GetSearchMultiUseCase,
) : ViewModel() {

    private val _searchQuery: MutableStateFlow<String> = MutableStateFlow("")
    private val _type: MutableStateFlow<Int> = MutableStateFlow(0)

    init {
        _type.value = 0
    }

    val movies = _searchQuery
        .flatMapLatest { query ->
            _type.flatMapLatest { type ->
                getSearchMultiUseCase.invoke(query, type).cachedIn(viewModelScope)
            }
        }

    fun searchMovies(searchQuery: String, type: Int) {
        _searchQuery.value = searchQuery
        _type.value = type
    }
}