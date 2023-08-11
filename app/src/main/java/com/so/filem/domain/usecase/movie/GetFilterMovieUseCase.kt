package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.data.remote.response.MovieResponse
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilteredMovieUseCase @Inject constructor(
    private val getNowPlayingMoviesUseCase: GetNowPlayingMovieUseCase,
    private val getUpcomingMoviesUseCase: GetUpComingMovieUseCase,
    private val getPopularMoviesUseCase: GetPopularMovieUseCase,
   private val getTopRatedMoviesUseCase: GetTopRatedMovieUseCase,
) {

    operator fun invoke(filter: MovieFilter): Flow<PagingData<Movie>> {
        return when (filter) {
            MovieFilter.NOW_PLAYING -> getNowPlayingMoviesUseCase(filter)
            MovieFilter.UPCOMING -> getUpcomingMoviesUseCase(filter)
            MovieFilter.POPULAR -> getPopularMoviesUseCase(filter)
            MovieFilter.TOP_RATED -> getTopRatedMoviesUseCase(filter)
        }
    }
}