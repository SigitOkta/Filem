package com.so.filem.ui.detail.tv

import androidx.annotation.StringRes
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.Trailer

const val DETAIL_TV_CAST = 0
const val DETAIL_TV_TRAILER = 1

sealed class OverViewDetailTvItem(val type: Int) {
    class DetailTvCast(
        @StringRes val title: Int,
        val castList: List<Cast>,
    ) : OverViewDetailTvItem(DETAIL_TV_CAST)

    class DetailTvTrailer(
        @StringRes val title: Int,
        val trailer: List<Trailer>,
    ) : OverViewDetailTvItem(DETAIL_TV_TRAILER)
}