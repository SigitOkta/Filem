package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTopRatedMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(filter: MovieFilter): Flow<PagingData<Movie>> {
        return movieRepository.getMovies(filter)
    }
}