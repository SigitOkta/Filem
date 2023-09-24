package com.so.filem.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentFavoriteBinding
import com.so.filem.databinding.FragmentHomeBinding
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.detail.cast.MediaContent
import com.so.filem.ui.detail.cast.MediaParentAdapter
import com.so.filem.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    override val viewModel: HomeViewModel by viewModels()

    override fun observeData() {
        super.observeData()
        setupRv()

    }

    private fun setupRv() {
        viewBinding().rvHome.setHasFixedSize(true)
        viewBinding().rvHome.layoutManager = LinearLayoutManager(
            requireContext()
        )

        prepareDataHome(viewBinding())
    }

    private fun prepareDataHome(viewBinding: FragmentHomeBinding) {
        val parentItemList = mutableListOf<HomeContent>()

        val titleTabTrendingMovie = listOf("day", "week")
        val titleTabTrendingTv = listOf("day", "week")
        val parentContentTrendingMovie =
            HomeContent(R.drawable.ic_trending, "Trending Movies", "movie", titleTabTrendingMovie)
        val parentContentTrendingTv =
            HomeContent(R.drawable.ic_tv_off_white, "Trending Tv", "tv", titleTabTrendingTv)
        parentItemList.add(parentContentTrendingMovie)
        parentItemList.add(parentContentTrendingTv)

        val adapter = HomeParentAdapter(parentItemList, requireActivity())
        viewBinding.rvHome.adapter = adapter
    }
}