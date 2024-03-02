/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.data.local.dao.tvShow.entity

import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.TvShow

fun TvsEntity.asTv() = TvShow(
    id = id,
    adult = adult,
    backdropPath = backdrop_path,
    original_language = original_language,
    original_name = original_name,
    overview = overview,
    posterPath = poster_path,
    first_air_date = first_air_date,
    name = name,
    vote_average = vote_average,
    vote_count = vote_count,
    isFavorite = isFavorite
)

fun List<TvsEntity>.asTvs() = this.map { it.asTv() }