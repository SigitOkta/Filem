package com.so.filem.domain.usecase.movie

import androidx.paging.PagingData
import com.so.filem.domain.model.MovieFilter
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFilterMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(filter: MovieFilter): Flow<PagingData<MoviePaging>> {
        return movieRepository.getMoviesForPaging(filter)
    }
}