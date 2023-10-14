package com.so.filem.ui.filter.tv

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentTvShowBinding
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.model.TvFilter
import com.so.filem.ui.adapter.LoadingStateAdapter
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TvShowFragment :
    BaseViewModelFragment<FragmentTvShowBinding, TvShowViewModel>(FragmentTvShowBinding::inflate) {
    override val viewModel: TvShowViewModel by viewModels()
    private val tvAdapter by lazy { TvListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        setActionBarTitle(viewModel.getFilterTitle(TvFilter.AIRING_TODAY))
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter_tv, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_airing_today -> {
                        setActionBarTitle(viewModel.getFilterTitle(TvFilter.AIRING_TODAY))
                        initData(TvFilter.AIRING_TODAY)
                        true
                    }

                    R.id.menu_on_the_air -> {
                        setActionBarTitle(viewModel.getFilterTitle(TvFilter.ON_THE_AIR))
                        initData(TvFilter.ON_THE_AIR)
                        true
                    }

                    R.id.menu_popular_tv -> {
                        setActionBarTitle(viewModel.getFilterTitle(TvFilter.POPULAR))
                        initData(TvFilter.POPULAR)
                        true
                    }

                    R.id.menu_top_rated_tv -> {
                        setActionBarTitle(viewModel.getFilterTitle(TvFilter.TOP_RATED))
                        initData(TvFilter.TOP_RATED)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
        initData(TvFilter.AIRING_TODAY)
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().rvGrid
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = tvAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                tvAdapter.retry()
            }
        )
    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch{
            viewModel.tv.collectLatest { tv ->
                if (tv != null) {
                    tvAdapter.submitData(lifecycle,tv)
                }
            }
        }
    }

    private fun initData(filter: TvFilter) {
        lifecycleScope.launch {
            viewModel.setFilter(filter)
        }
    }

    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity?)?.setSupportActionBar(viewBinding().toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = title

    }
}