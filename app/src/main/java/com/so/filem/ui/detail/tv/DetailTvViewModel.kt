/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.tv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.usecase.movie.GetMovieDetailsUseCase
import com.so.filem.domain.usecase.tv.DeleteFavoriteTvUseCase
import com.so.filem.domain.usecase.tv.GetFavoriteTvUseCase
import com.so.filem.domain.usecase.tv.GetTvDetailsUseCase
import com.so.filem.domain.usecase.tv.SaveFavoriteTvUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailTvViewModel @Inject constructor(
    private val getTvDetailsUseCase: GetTvDetailsUseCase,
    private val getFavoriteTvUseCase: GetFavoriteTvUseCase,
    private val saveFavoriteTvUseCase: SaveFavoriteTvUseCase,
    private val deleteFavoriteTvUseCase: DeleteFavoriteTvUseCase,
) : ViewModel(){

    private val _tvId = MutableStateFlow<Long?>(null)
    val tvId: StateFlow<Long?> = _tvId

    private val _tvDetails = MutableStateFlow<Resource<TvDetails>?>(null)
    val tvDetails: StateFlow<Resource<TvDetails>?> = _tvDetails

    private val _isFavorite = MutableStateFlow<Boolean?>(null)
    val isFavorite: StateFlow<Boolean?> = _isFavorite

    init {
        viewModelScope.launch {
            try {
                _tvId.collect { id ->
                    if (id != null) {
                        getTvDetails(id)
                        getIsFavorite(id)
                    }
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
            Timber.tag("viewModel-init").d(_isFavorite.value.toString())
        }

    }
    fun getMovieId(id: Long) {
        _tvId.value = id
        Timber.tag("viewModel-id").d(id.toString())
    }

    //ambil value isFavorite
    private fun getIsFavorite(movieId: Long) {
        viewModelScope.launch {
            _isFavorite.value = null // Setel null saat memulai pemanggilan
            try {
                getFavoriteTvUseCase.invoke(movieId).collect{
                    _isFavorite.value = it?.isFavorite
                    Timber.tag("viewModel-isFavorite").d(_isFavorite.value.toString())
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    //ambil data movie dengan id
    private fun getTvDetails(tvId: Long) {
        viewModelScope.launch {
            _tvDetails.value = Resource.Loading()
            try {
                val remote = getTvDetailsUseCase(tvId)
                _tvDetails.value = Resource.Success(remote)
                Timber.tag("viewModel").d(remote.tvShow.id.toString())
            } catch (e: Exception) {
                Timber.tag("viewModel").e(e)
                _tvDetails.value = Resource.Error(e)
            }
        }
    }

    //jika favorite di klik
    fun onFavoriteClicked(){
        viewModelScope.launch {
            val id = _tvId.value ?: return@launch
            when (_isFavorite.value) {
                true -> {
                    val deleted = deleteFavoriteTvUseCase(id)
                    _isFavorite.value = deleted == 1
                    Timber.d("asd deleted from favorites $deleted movie with id = $tvId")
                }
                else -> {
                    val savedId = _tvDetails.value?.payload?.let { saveFavoriteTvUseCase(it.tvShow) }
                    _isFavorite.value = savedId?.equals(tvId)
                    Timber.d("asd saved to favorites $savedId movie with id = $tvId")
                }
            }
        }
    }
}