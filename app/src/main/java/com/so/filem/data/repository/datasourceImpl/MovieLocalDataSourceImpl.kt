package com.so.filem.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.paging.MovieRemoteMediator
import com.so.filem.data.remote.network.ApiService
import com.so.filem.domain.model.MovieFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val api: ApiService,
    private val db: TMDBDatabase,
) : MovieLocalDataSource {
    private val moviePagingDao = db.moviePagingDao()
    private val movieDao = db.movieDao()
    override fun getMoviesFromDB(movieId: Int): Flow<MoviePaging> = moviePagingDao.getMovie(movieId)

    /*override fun getMovies(filter: MovieFilter): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = 20),
            pagingSourceFactory = {
                MoviePagingSource(
                    api = api,
                    filter = filter
                )
            }
        ).flow
    }*/

    @OptIn(ExperimentalPagingApi::class)
    override fun getMoviesForPaging(filter : MovieFilter): Flow<PagingData<MoviePaging>> {
        val pagingSourceFactory = { moviePagingDao.getAllMovies() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = MovieRemoteMediator(
                api,
                db,
                filter
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }

    override suspend fun saveFavorite(movie: MoviesEntity): Long {
        return movieDao.insertMovie(movie)
    }

    override fun getFavoriteMovie(movieId: Long): Flow<MoviesEntity?> {
        return movieDao.getMovie(movieId)
    }

    override fun getAllFavoriteMovies(): Flow<List<MoviesEntity>> {
        return movieDao.getAllFavoriteMovies()
    }

    override suspend fun updateFavorite(movieId: String, isFavorite: Boolean) {
        return movieDao.updateFavorite(movieId, isFavorite)
    }

    override suspend fun deleteFavoriteMovie(movieId: Long): Int {
        return movieDao.deleteMovieById(movieId)
    }

    override suspend fun deleteAllFavoriteMovie() {
        return movieDao.deleteMovies()
    }

    override suspend fun deleteMoviesWithNoFav(): Int {
        return movieDao.deleteMoviesWithNoFav()
    }

    override suspend fun movieExists(id: Long): Boolean {
        return movieDao.movieExists(id)
    }
}