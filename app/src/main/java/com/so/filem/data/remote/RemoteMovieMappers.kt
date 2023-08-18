package com.so.filem.data.remote

import com.so.filem.data.remote.response.MoviesListResponse
import com.so.filem.data.remote.response.MoviesResponse
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.model.movie.Movies

fun MoviesResponse.asMovies() = Movies(
    page = page,
    results = results.map { it.asMovie() },
    total_pages = total_pages,
    total_results = total_results
)

fun MoviesListResponse.asMovie() = Movie(
    id = id,
    adult = adult,
    backdropPath = backdrop_path,
    original_language = original_language,
    original_title = original_title,
    overview = overview,
    popularity = popularity,
    posterPath = poster_path,
    release_date = release_date,
    title = title,
    vote_average = vote_average,
    vote_count = vote_count,
    isFavorite = null
)