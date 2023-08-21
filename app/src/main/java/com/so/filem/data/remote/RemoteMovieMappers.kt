package com.so.filem.data.remote

import com.so.filem.data.remote.response.MovieDetailsResponse
import com.so.filem.data.remote.response.MoviesListResponse
import com.so.filem.data.remote.response.MoviesResponse
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.Genre
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.Trailer

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



fun MovieDetailsResponse.asMovie(): Movie {
    return Movie(
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
}

fun MovieDetailsResponse.asGenres(): List<Genre> {
    return genres?.map {
        Genre(
            id = it.id,
            movieId = id,
            name = it.name,
        )
    }?.toList() ?: emptyList()
}

fun MovieDetailsResponse.asCast(): List<Cast> {
    return creditsResponse.cast?.map {
        Cast(
            id = it.id,
            movieId = id,
            actorName = it.actorName,
            profileImagePath = it.profileImagePath,
        )
    }?.toList() ?: emptyList()
}

fun MovieDetailsResponse.asVideos(): List<Trailer> {
    return videosResponse.videos?.map {
        Trailer(
            id = it.id,
            movieId = id,
            key = it.key,
            name = it.name,
        )
    }?.toList() ?: emptyList()
}

fun MovieDetailsResponse.asMovieDetails(): MovieDetails {
    return MovieDetails(
        movie = asMovie(),
        genres = asGenres(),
        cast = asCast(),
        trailers = asVideos(),
    )
}