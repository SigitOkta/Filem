package com.so.filem.domain.repository

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Tvs
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMoviesForPaging(filter: MovieFilter): Flow<PagingData<MoviePaging>>
    fun getMoviesFromDB(movieId: Int): Flow<MoviePaging>
    suspend fun getMovieDetails(movieId: Long): MovieDetails

    suspend fun saveFavoriteMovie(movie: Movie): Long
    suspend fun updateFavorite(movieId: String, isFavorite: Boolean)
    suspend fun deleteFavoriteMovie(movieId: Long): Int
    suspend fun deleteAllFavoriteMovies()
    fun getFavoriteMovie(movieId: Long): Flow<Movie?>
    fun getAllFavoriteMovies(): Flow<List<Movie>>

    suspend fun deleteMoviesWithNoFav() : Int
    suspend fun movieExists(movieId : Long) : Boolean

    fun getSearchMoviesForPaging(query: String): Flow<PagingData<Movie>>

    suspend fun discoverMovie() : Movies
    suspend fun discoverTv() : Tvs
    suspend fun getCastDetail(castId : Long) : CastDetails
    suspend fun getTrendingMovie(timeWindow : String) : Movies
    suspend fun getTrendingTv(timeWindow : String) : Tvs
}
