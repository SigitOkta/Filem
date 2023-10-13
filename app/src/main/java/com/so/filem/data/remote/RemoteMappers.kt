package com.so.filem.data.remote

import com.so.filem.data.remote.response.CastDetailsResponse
import com.so.filem.data.remote.response.CastResponse
import com.so.filem.data.remote.response.CastsResponse
import com.so.filem.data.remote.response.MovieDetailsResponse
import com.so.filem.data.remote.response.MoviesListResponse
import com.so.filem.data.remote.response.MoviesResponse
import com.so.filem.data.remote.response.TvDetailsResponse
import com.so.filem.data.remote.response.TvListResponse
import com.so.filem.data.remote.response.TvResponse
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.model.Casts
import com.so.filem.domain.model.Genre
import com.so.filem.domain.model.MediaItem
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Movies
import com.so.filem.domain.model.ProfilesItem
import com.so.filem.domain.model.Season
import com.so.filem.domain.model.Trailer
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.model.TvShow
import com.so.filem.domain.model.Tvs

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
    runtime = runtime,
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
        runtime = runtime,
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

fun CastDetailsResponse.asCastDetails(): CastDetails {
    return CastDetails(
        id = id,
        adult = adult,
        profilePath = profilePath,
        name = name,
        gender = gender,
        birthday = birthday,
        deathDay = deathDay,
        placeOfBirth = placeOfBirth,
        knownForDepartment = knownForDepartment,
        biography = biography,
        alsoKnownAs = alsoKnownAs,
        combinedCredits = asCombinedCredits(),
        images = asImages()
    )
}

fun CastDetailsResponse.asCombinedCredits(): List<MediaItem> {
    return combinedCreditsResponse.cast?.map {
        MediaItem(
           id = it.id,
           name = it.name,
           title = it.title,
           character = it.character,
           posterPath = it.posterPath,
           mediaType = it.mediaType,
           adult = it.adult,
        )
    }?.toList() ?: emptyList()
}

fun CastDetailsResponse.asImages(): List<ProfilesItem> {
    return imagesResponse.profiles?.map {
        ProfilesItem(
            filePath = it.filePath
        )
    }?.toList() ?: emptyList()
}

fun TvResponse.asTvs() = Tvs(
    page = page,
    results = results.map { it.asTv() },
    total_pages = total_pages,
    total_results = total_results
)

fun TvListResponse.asTv() = TvShow (
    id = id,
    adult = adult,
    backdropPath = backdrop_path,
    original_language = original_language,
    original_name =  original_name,
    overview = overview,
    posterPath = poster_path,
    first_air_date = first_air_date,
    name = name,
    vote_average = vote_average,
    vote_count = vote_count,
    isFavorite = null
)

fun CastsResponse.asCasts() = Casts(
    page = page,
    results = results.map { it.asCast() },
    total_pages = total_pages,
    total_results = total_results
)

fun CastResponse.asCast() = Cast (
    id = id,
    movieId = 0,
    actorName = actorName,
    profileImagePath = profileImagePath,
)

fun TvDetailsResponse.asTv(): TvShow {
    return TvShow(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        original_language = originalLanguage,
        original_name = originalName,
        overview = overview,
        posterPath = posterPath,
        first_air_date = firstAirDate,
        name = name,
        vote_average = voteAverage,
        vote_count = voteCount,
        isFavorite = null
    )
}

fun TvDetailsResponse.asGenres(): List<Genre> {
    return genres?.map {
        Genre(
            id = it.id,
            movieId = id,
            name = it.name,
        )
    }?.toList() ?: emptyList()
}

fun TvDetailsResponse.asCast(): List<Cast> {
    return creditsResponse.cast?.map {
        Cast(
            id = it.id,
            movieId = id,
            actorName = it.actorName,
            profileImagePath = it.profileImagePath,
        )
    }?.toList() ?: emptyList()
}

fun TvDetailsResponse.asVideos(): List<Trailer> {
    return videosResponse.videos?.map {
        Trailer(
            id = it.id,
            movieId = id,
            key = it.key,
            name = it.name,
        )
    }?.toList() ?: emptyList()
}

fun TvDetailsResponse.asTvDetails(): TvDetails {
    return TvDetails(
        tvShow = asTv(),
        genres = asGenres(),
        cast = asCast(),
        trailers = asVideos(),
        seasons = asSeasons(),
    )
}

fun TvDetailsResponse.asSeasons(): List<Season> {
    return seasonsResponse?.map {
        Season(
            id = it.id,
            airDate = it.airDate,
            episodeCount = it.episodeCount,
            name = it.name,
            overview = it.overview,
            posterPath = it.posterPath,
            seasonNumber = it.seasonNumber,
            voteAverage = it.voteAverage,
        )
    }?.toList() ?: emptyList()
}