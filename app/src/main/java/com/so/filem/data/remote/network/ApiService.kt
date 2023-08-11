package com.so.filem.data.remote.network

import com.so.filem.data.remote.response.MovieResponse
import com.so.filem.domain.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): Response<MovieResponse>

    @GET(Constants.UPCOMING)
    suspend fun getUpComing(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): Response<MovieResponse>

    @GET(Constants.POPULAR)
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): Response<MovieResponse>

    @GET(Constants.TOP_RATED)
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): Response<MovieResponse>
}