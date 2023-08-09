package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.data.remote.response.MovieResponse
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredMovieUseCase @Inject constructor(
    /*private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,*/
    private val getNowPlayingMoviesUseCase: GetNowPlayingMovieUseCase,
    private val getUpcomingMoviesUseCase: GetUpComingMovieUseCase,
) {

    operator fun invoke(filter: MovieFilter): Flow<PagingData<Movie>> {
        return when (filter) {
            /* MovieFilter.POPULAR -> getPopularMoviesUseCase(page)
             MovieFilter.TOP_RATED -> getTopRatedMoviesUseCase(page)*/
            MovieFilter.NOW_PLAYING -> getNowPlayingMoviesUseCase(filter)
            MovieFilter.UPCOMING -> getUpcomingMoviesUseCase(filter)
        }
    }
}