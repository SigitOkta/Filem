/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository.datasource

import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.Tvs

interface TvRemoteDataSource {
    suspend fun getTvDetails(tvId: Long) : TvDetails
    suspend fun getDiscoverTv(): Tvs
    suspend fun getTrendingTv(timeWindow: String) : Tvs


}