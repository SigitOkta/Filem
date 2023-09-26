package com.so.filem.data.remote.network

import com.so.filem.data.remote.response.CastDetailsResponse
import com.so.filem.data.remote.response.MovieDetailsResponse
import com.so.filem.data.remote.response.MoviesResponse
import com.so.filem.data.remote.response.TvResponse
import com.so.filem.domain.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Constants.NOW_PLAYING)
    suspend fun getNowPlaying(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.UPCOMING)
    suspend fun getUpComing(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.POPULAR)
    suspend fun getPopular(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.TOP_RATED)
    suspend fun getTopRated(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.MOVIE_DETAIL)
    suspend fun getMovieDetails(
        @Path("id") id: Long
    ): MovieDetailsResponse

    @GET(Constants.MOVIE_SEARCH)
    suspend fun getMovieSearch(
        @Query("query") query: String,
        @Query("page") page: Int
    ): MoviesResponse

    @GET(Constants.DISCOVER_MOVIE)
    suspend fun getDiscoverMovie(
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.DISCOVER_TV)
    suspend fun getDiscoverTv(
        @Query("language") language: String = "en-US"
    ): TvResponse

    @GET(Constants.CAST_DETAIL)
    suspend fun getCastDetail(
        @Path("person_id") id: Long
    ): CastDetailsResponse

    @GET(Constants.TRENDING_MOVIE)
    suspend fun getTrendingMovie(
        @Path("time_window") time: String,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET(Constants.TRENDING_TV)
    suspend fun getTrendingTv(
        @Path("time_window") time: String,
        @Query("language") language: String = "en-US"
    ): TvResponse
}