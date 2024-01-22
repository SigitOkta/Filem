package com.so.filem.data.remote.network

import com.so.filem.data.remote.response.CastDetailsResponse
import com.so.filem.data.remote.response.CastsResponse
import com.so.filem.data.remote.response.MovieDetailsResponse
import com.so.filem.data.remote.response.MoviesResponse
import com.so.filem.data.remote.response.SearchMultiResponse
import com.so.filem.data.remote.response.TvDetailsResponse
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

    @GET(Constants.AIRING_TODAY)
    suspend fun getAiringToday(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TvResponse

    @GET(Constants.ON_THE_AIR)
    suspend fun getOnTheAir(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TvResponse

    @GET(Constants.POPULAR_TV)
    suspend fun getPopularTv(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TvResponse

    @GET(Constants.TOP_RATED_TV)
    suspend fun getTopRatedTv(
        @Query("page") page: Int,
        @Query("language") language: String = "en-US"
    ): TvResponse

    @GET(Constants.MOVIE_DETAIL)
    suspend fun getMovieDetails(
        @Path("id") id: Long
    ): MovieDetailsResponse

    @GET(Constants.SEARCH_MULTI)
    suspend fun getSearchMulti(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMultiResponse

    @GET(Constants.SEARCH_MOVIE)
    suspend fun getSearchMovie(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMultiResponse
    @GET(Constants.SEARCH_TV)
    suspend fun getSearchTv(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMultiResponse
    @GET(Constants.SEARCH_PERSON)
    suspend fun getSearchPerson(
        @Query("query") query: String,
        @Query("page") page: Int
    ): SearchMultiResponse
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

    @GET(Constants.PEOPLE_POPULAR)
    suspend fun getPopularPeople(
        @Query("language") language: String = "en-US"
    ): CastsResponse

    @GET(Constants.TV_DETAIL)
    suspend fun getTvDetails(
        @Path("id") id: Long
    ): TvDetailsResponse
}