package com.so.filem.domain.usecase.movie

import com.so.filem.domain.model.Cast
import com.so.filem.domain.repository.MovieRepository
import javax.inject.Inject

class GetPopularPeopleUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(): List<Cast> {
        return movieRepository.getPopularPeople().results
    }
}