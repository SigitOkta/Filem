package com.so.filem.data.repository.datasourceImpl

import com.so.filem.data.remote.asTvDetails
import com.so.filem.data.remote.asTvs
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.data.repository.datasource.TvRemoteDataSource
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.Tvs
import javax.inject.Inject

class TvRemoteDataSourceImpl @Inject constructor(
    private val api: ApiService,
) : TvRemoteDataSource {
    override suspend fun getTvDetails(tvId: Long): TvDetails {
        return api.getTvDetails(tvId).asTvDetails()
    }

    override suspend fun getDiscoverTv(): Tvs {
        return api.getDiscoverTv().asTvs()
    }

    override suspend fun getTrendingTv(timeWindow: String): Tvs {
        return api.getTrendingTv(timeWindow).asTvs()
    }

}