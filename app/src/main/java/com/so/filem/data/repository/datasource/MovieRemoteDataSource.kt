package com.so.filem.data.repository.datasource

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Casts
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Search
import kotlinx.coroutines.flow.Flow

interface MovieRemoteDataSource {

    //SearchMulti
    /*fun getSearchMultiPaging(query: String)*/
    fun getSearch(query: String, type: Int) : LiveData<PagingData<Search>>


    suspend fun getMovieDetails(movieId: Long) : MovieDetails
    suspend fun getDiscoverMovie(): Movies

    suspend fun getCastDetails(castId: Long) : CastDetails

    suspend fun getTrendingMovie(timeWindow: String) : Movies
    suspend fun getPopularPeople() : Casts
}