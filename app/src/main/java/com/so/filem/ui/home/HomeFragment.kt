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
        viewModel.parentData.observe(viewLifecycleOwner){
            prepareDataHome(viewBinding(), it)
        }
    }

    override fun initView() {
        super.initView()
        viewModel.getParentData()
    }

    private fun setupRv() {
        viewBinding().rvHome.setHasFixedSize(true)
        viewBinding().rvHome.layoutManager = LinearLayoutManager(
            requireContext()
        )

    }

    private fun prepareDataHome(viewBinding: FragmentHomeBinding, homeItem: List<HomeItem>) {
        /*val parentItemList = mutableListOf<HomeContent>()

        val titleTabTrendingMovie = listOf("day", "week")
        val titleTabTrendingTv = listOf("day", "week")

        val parentContentTrendingMovie =
            HomeContent(R.drawable.ic_trending, "Trending Movies", "movie", titleTabTrendingMovie)

        val parentContentTrendingTv =
            HomeContent(R.drawable.ic_tv_off_white, "Trending Tv", "tv", titleTabTrendingTv)
        parentItemList.add(parentContentTrendingMovie)
        parentItemList.add(parentContentTrendingTv)*/

        val adapter = HomeParentAdapter(homeItem, requireActivity())
        viewBinding.rvHome.adapter = adapter
    }
}