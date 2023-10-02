package com.so.filem.data.repository.datasource

import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.Tvs

interface TvRemoteDataSource {
    suspend fun getTvDetails(tvId: Long) : TvDetails
    suspend fun getDiscoverTv(): Tvs
    suspend fun getTrendingTv(timeWindow: String) : Tvs


}