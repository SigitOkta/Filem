package com.so.filem.ui.detail.cast

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.usecase.movie.GetCastDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailCastViewModel @Inject constructor(
    private val getCastDetailsUseCase: GetCastDetailsUseCase
) : ViewModel() {

    private var castId: Long = 0

    private val _castDetails = MutableLiveData<CastDetails>()
    val castDetails: LiveData<CastDetails> = _castDetails


    fun setCastId(id: Long) {
        castId = id
        getCastDetails()
    }
    private fun getCastDetails() {
        viewModelScope.launch {
            try {
                val result = getCastDetailsUseCase(castId)
                _castDetails.value = result
                Timber.tag("viewModelCast").d(result.name)

            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }
}
