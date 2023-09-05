package com.so.filem.ui.movie.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.usecase.movie.DeleteFavoriteMovieUseCase
import com.so.filem.domain.usecase.movie.DeleteMoviesWithNoFavUseCase
import com.so.filem.domain.usecase.movie.GetFavoriteMovieUseCase
import com.so.filem.domain.usecase.movie.GetMovieDetailsUseCase
import com.so.filem.domain.usecase.movie.GetMovieUseCase
import com.so.filem.domain.usecase.movie.MovieExistsUseCase
import com.so.filem.domain.usecase.movie.SaveFavoriteMovieUseCase
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailMovieViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val getFavoriteMovieUseCase: GetFavoriteMovieUseCase,
    private val saveFavoriteMovieUseCase: SaveFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
    private val movieExistsUseCase: MovieExistsUseCase,
    private val deleteMoviesWithNoFavUseCase: DeleteMoviesWithNoFavUseCase
) : ViewModel() {

    private val _movieId = MutableStateFlow<Long?>(null)
    val movieId: StateFlow<Long?> = _movieId

    private val _movieDetails = MutableStateFlow<Resource<MovieDetails>?>(null)
    val movieDetails: StateFlow<Resource<MovieDetails>?> = _movieDetails

    private val _isFavorite = MutableStateFlow<Boolean?>(null)
    val isFavorite: StateFlow<Boolean?> = _isFavorite

    //cek id
    init {
        viewModelScope.launch {
            try {
                _movieId.collect { id ->
                    if (id != null) {
                        getMovieDetails(id)
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
        _movieId.value = id
        Timber.tag("viewModel-id").d(id.toString())
    }

    //ambil value isFavorite
    private fun getIsFavorite(movieId: Long) {
        viewModelScope.launch {
            _isFavorite.value = null // Setel null saat memulai pemanggilan
            try {
                getFavoriteMovieUseCase(movieId).collect{
                    _isFavorite.value = it?.isFavorite
                    Timber.tag("viewModel-isFavorite").d(_isFavorite.value.toString())
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }
    }

    //ambil data movie dengan id
    private fun getMovieDetails(movieId: Long) {
        viewModelScope.launch {
            _movieDetails.value = Resource.Loading()
            try {
               val isFavorite: Boolean
                val remote = getMovieDetailsUseCase(movieId)
              /* val fav = movieExistsUseCase(remote.movie.id)
                isFavorite = fav
                deleteMoviesWithNoFavUseCase()
                saveFavoriteMovieUseCase(remote.movie, isFavorite)*/

                _movieDetails.value = Resource.Success(remote)
                Timber.tag("viewModel").d(remote.movie.id.toString())
            } catch (e: Exception) {
                Timber.tag("viewModel").e(e)
                _movieDetails.value = Resource.Error(e)
            }
        }
    }

    //jika favorite di klik
    fun onFavoriteClicked(){
        viewModelScope.launch {
            val id = _movieId.value ?: return@launch
            when (_isFavorite.value) {
                true -> {
                    val deleted = deleteFavoriteMovieUseCase(id)
                    _isFavorite.value = deleted == 1
                    Timber.d("asd deleted from favorites $deleted movie with id = $movieId")
                }
                else -> {
                    val savedId = _movieDetails.value?.payload?.let { saveFavoriteMovieUseCase(it.movie) }
                    _isFavorite.value = savedId?.equals(movieId)
                    Timber.d("asd saved to favorites $savedId movie with id = $movieId")
                }
            }
        }
    }
}