/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.tv

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentDetailTvShowBinding
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.utils.parcelable
import com.so.filem.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DetailTvShowFragment : BaseViewModelFragment<FragmentDetailTvShowBinding, DetailTvViewModel>(
    FragmentDetailTvShowBinding::inflate
) {
    override val viewModel: DetailTvViewModel by viewModels()

    private var argumentValue: Int = 0
    override fun observeData() {
        super.observeData()
        argumentValue = arguments?.getInt("arg_key") ?: 0
        val tvShowDetailItem = arguments?.parcelable<TvDetails>("arg_bundle")
        Timber.tag("detailtvoverview").d(tvShowDetailItem.toString())
        if (tvShowDetailItem != null) {
            if (argumentValue == 0) {
                setupRvOverview(tvShowDetailItem)
            } else {
                setupRvSeasons(tvShowDetailItem)
            }
        }
    }

    private fun setupRvSeasons(tvShowDetailItem: TvDetails) {
        val rvOverView = viewBinding().rvOverviewTv
        rvOverView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = DetailTvSeasonAdapter(tvShowDetailItem.seasons)
        rvOverView.adapter = adapter
    }

    private fun setupRvOverview(tvShowDetailItem: TvDetails) {
        val overViewDetailTvItem = mutableListOf<OverViewDetailTvItem>()
        overViewDetailTvItem.apply {
            add(
                OverViewDetailTvItem.DetailTvCast(
                    R.string.en_text_title_cast, tvShowDetailItem.cast
                )
            )
            add(
                OverViewDetailTvItem.DetailTvTrailer(
                    R.string.en_text_title_video, tvShowDetailItem.trailers
                )
            )

        }
        val rvOverView = viewBinding().rvOverviewTv
        rvOverView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        val adapter = DetailTvOverviewAdapter(overViewDetailTvItem)
        rvOverView.adapter = adapter
    }

}