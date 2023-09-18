package com.so.filem.data.repository

import androidx.paging.PagingData
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.data.local.dao.movie.entity.asMovie
import com.so.filem.data.local.dao.movie.entity.asMovies
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource
) : MovieRepository {
    override fun getMoviesForPaging(filter: MovieFilter) = localDataSource.getMoviesForPaging(filter)
    override fun getMoviesFromDB(movieId: Int): Flow<MoviePaging> {
        return localDataSource.getMoviesFromDB(movieId)
    }

    override suspend fun getMovieDetails(movieId: Long): MovieDetails {
        return remoteDataSource.getMovieDetails(movieId)
    }

    override suspend fun saveFavoriteMovie(movie: Movie): Long {
        return localDataSource.saveFavorite(
            MoviesEntity(
                id = movie.id,
                adult = movie.adult,
                backdrop_path = movie.backdropPath,
                original_language = movie.original_language,
                original_title = movie.original_title,
                overview = movie.overview,
                popularity = movie.popularity,
                poster_path = movie.posterPath,
                release_date = movie.release_date,
                title = movie.title,
                vote_average = movie.vote_average,
                vote_count = movie.vote_count,
                runtime = movie.runtime,
                isFavorite = true
            )
        )
    }

    override suspend fun updateFavorite(movieId: String, isFavorite: Boolean) {
        return localDataSource.updateFavorite(movieId, isFavorite)
    }

    override suspend fun deleteFavoriteMovie(movieId: Long): Int {
        return localDataSource.deleteFavoriteMovie(movieId)
    }

    override suspend fun deleteAllFavoriteMovies() {
        return localDataSource.deleteAllFavoriteMovie()
    }

    override fun getFavoriteMovie(movieId: Long): Flow<Movie?> {
        return localDataSource.getFavoriteMovie(movieId).map { it?.asMovie() }
    }

    override fun getAllFavoriteMovies(): Flow<List<Movie>> {
        return localDataSource.getAllFavoriteMovies().map { it.asMovies() }
    }

    override suspend fun deleteMoviesWithNoFav(): Int {
        return localDataSource.deleteMoviesWithNoFav()
    }

    override suspend fun movieExists(movieId: Long): Boolean {
        return localDataSource.movieExists(movieId)
    }

    override fun getSearchMoviesForPaging(query: String): Flow<PagingData<Movie>> {
        return remoteDataSource.getSearchMoviePaging(query)
    }

    override suspend fun discoverMovie(): Movies {
        return remoteDataSource.getDiscoverMovie()
    }

    override suspend fun getCastDetail(castId: Long): CastDetails {
        return remoteDataSource.getCastDetails(castId)
    }


}