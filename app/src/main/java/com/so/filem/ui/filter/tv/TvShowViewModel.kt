/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.filter.tv

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.so.filem.R
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.domain.model.TvFilter
import com.so.filem.domain.usecase.movie.GetFilterTvUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TvShowViewModel @Inject constructor(
    private val getFilterTvUseCase: GetFilterTvUseCase
) : ViewModel() {
    private val _tv = MutableStateFlow<PagingData<TvPaging>?>(null)
    private var currentFilter: TvFilter? = null

    val tv : Flow<PagingData<TvPaging>?>
        get() = _tv

    fun setFilter(filter: TvFilter){
        currentFilter = filter
        getFilterTv()
    }

    private fun getFilterTv() {
        val filter = currentFilter ?: return
        viewModelScope.launch {
            getFilterTvUseCase(filter)
                .catch {
                    Timber.e(it)
                }
                .collect {
                    _tv.value = it
                }
        }
    }

    fun getFilterTitle(context: Context, filter: TvFilter): String {
        return when (filter) {
            TvFilter.AIRING_TODAY -> {
                ContextCompat.getString(context, R.string.en_text_filter_airing_today)
            }
            TvFilter.ON_THE_AIR -> {
                ContextCompat.getString(context, R.string.en_text_filter_on_the_air)
            }
            TvFilter.POPULAR -> {
                ContextCompat.getString(context, R.string.en_text_filter_popular)
            }
            TvFilter.TOP_RATED -> {
                ContextCompat.getString(context, R.string.en_text_filter_top_rated)
            }
        }
    }
}