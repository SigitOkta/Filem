package com.so.filem.domain.utils

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"

    const val NOW_PLAYING = "movie/now_playing"
    const val UPCOMING = "movie/upcoming"
    const val POPULAR = "movie/popular"
    const val TOP_RATED = "movie/top_rated"
    const val DISCOVER_MOVIE = "discover/movie"
    const val DISCOVER_TV = "discover/movie"


    const val MOVIE_DETAIL = "movie/{id}?append_to_response=videos,credits"

    const val MOVIE_SEARCH = "search/movie"

    const val CAST_DETAIL = "person/{person_id}?append_to_response=images,combined_credits"

    const val TRENDING_MOVIE = "trending/movie/{time_window}"
    const val TRENDING_TV = "trending/tv/{time_window}"


    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"
    private const val IMAGE_SIZE_W185 = "w185"
    private const val IMAGE_SIZE_W780 = "w780"

    const val CAST_AVATAR_URL = IMAGE_BASE_URL + IMAGE_SIZE_W185
    const val CAST_IMDB_URL = "https://www.imdb.com/name/"
    const val POSTER_URL = IMAGE_BASE_URL + IMAGE_SIZE_W185
    const val BACKDROP_URL = IMAGE_BASE_URL + IMAGE_SIZE_W780
}