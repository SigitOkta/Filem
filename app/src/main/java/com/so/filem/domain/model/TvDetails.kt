package com.so.filem.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TvDetails(
    val tvShow: TvShow,
    val genres: List<Genre>,
    val cast: List<Cast>,
    val trailers: List<Trailer>,
    val seasons: List<Season>
): Parcelable
