package com.so.filem.ui.movie.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentMovieBinding
import com.so.filem.domain.model.MovieFilter
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.adapter.LoadingStateAdapter
import com.so.filem.ui.adapter.MovieListAdapter
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment :
    BaseViewModelFragment<FragmentMovieBinding, MovieViewModel>(FragmentMovieBinding::inflate) {
    override val viewModel: MovieViewModel by viewModels()
    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var loadingStateAdapter: LoadingStateAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        setActionBarTitle(viewModel.getFilterTitle(MovieFilter.NOW_PLAYING))
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_now_playing -> {
                        setActionBarTitle(viewModel.getFilterTitle(MovieFilter.NOW_PLAYING))
                        initData(MovieFilter.NOW_PLAYING)
                        true
                    }

                    R.id.menu_upcoming -> {
                        setActionBarTitle(viewModel.getFilterTitle(MovieFilter.UPCOMING))
                        initData(MovieFilter.UPCOMING)
                        true
                    }

                    R.id.menu_popular -> {
                        setActionBarTitle(viewModel.getFilterTitle(MovieFilter.POPULAR))
                        initData(MovieFilter.POPULAR)
                        true
                    }

                    R.id.menu_top_rated -> {
                        setActionBarTitle(viewModel.getFilterTitle(MovieFilter.TOP_RATED))
                        initData(MovieFilter.TOP_RATED)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (savedInstanceState == null) {
            // Save data state to handle screen orientation
            initData(MovieFilter.NOW_PLAYING)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
    }

    private fun initData(filter: MovieFilter) {
        lifecycleScope.launch {
            viewModel.getMovieList(filter)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().rvGrid
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        movieAdapter = MovieListAdapter()
        recyclerView.adapter = movieAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                movieAdapter.retry()
            }
        )
        viewModel.getMovieResult.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Empty -> {

                }
                is Resource.Error -> {
                    LoadingDialog.hideLoading()
                }
                is Resource.Loading -> LoadingDialog.startLoading(requireContext())
                is Resource.Success -> {
                    LoadingDialog.hideLoading()
                    it.payload?.let { movie ->
                        movieAdapter.submitData(lifecycle,movie)
                    }
                }
            }
        }
    }

    override fun observeData() {
        super.observeData()

    }

    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity?)?.setSupportActionBar(viewBinding().toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}