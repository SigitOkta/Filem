package com.so.filem.data.local.dao.movie.entity

import com.so.filem.domain.model.Movie


fun MoviesEntity.asMovie() = Movie(
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
    isFavorite = isFavorite
)

fun List<MoviesEntity>.asMovies() = this.map { it.asMovie() }