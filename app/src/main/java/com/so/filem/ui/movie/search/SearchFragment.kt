package com.so.filem.ui.movie.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentMovieBinding
import com.so.filem.databinding.FragmentSearchBinding
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.adapter.LoadingStateAdapter
import com.so.filem.ui.adapter.MovieListAdapter
import com.so.filem.ui.adapter.SearchListAdapter
import com.so.filem.ui.base.BaseFragment
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.custom.LoadingDialog
import com.so.filem.ui.movie.filter.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : BaseViewModelFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy { SearchListAdapter() }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
        inputSearch()
    }

    private fun inputSearch() {
        val searchView = viewBinding().includeSearchBar.svMovie
        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.updateQuery(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        searchView.setOnCloseListener {
            viewBinding().includeNotFound.root.visibility = View.GONE
            viewBinding().includeViewPager.root.visibility = View.VISIBLE
            viewBinding().includeRecyclerView.root.visibility = View.GONE
            true
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().includeRecyclerView.rvSearchItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = searchAdapter
        recyclerView.adapter = searchAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                searchAdapter.retry()
            }
        )
        searchAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.Error || searchAdapter.itemCount == 0
            if (isListEmpty) {
                viewBinding().includeNotFound.root.visibility = View.VISIBLE
                viewBinding().includeViewPager.root.visibility = View.GONE
                viewBinding().includeRecyclerView.root.visibility = View.GONE
            }else{
                viewBinding().includeNotFound.root.visibility = View.GONE
                viewBinding().includeViewPager.root.visibility = View.GONE
                viewBinding().includeRecyclerView.root.visibility = View.VISIBLE
            }
        }
    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.searchResults.collect { result ->
                when (result) {
                    //...
                    is Resource.Loading -> {
                        // Menampilkan ProgressBar saat pencarian sedang berlangsung
                        viewBinding().loadingProgressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        // Menampilkan hasil pencarian jika berhasil
                        viewBinding().loadingProgressBar.visibility = View.GONE
                        result.payload?.let {
                            searchAdapter.submitData(lifecycle,it)
                        }
                        viewBinding().includeNotFound.root.visibility = View.GONE
                        viewBinding().includeViewPager.root.visibility = View.GONE
                        viewBinding().includeRecyclerView.root.visibility = View.VISIBLE
                    }

                    is Resource.Error -> {
                        // Menampilkan pesan kesalahan jika terjadi kesalahan
                        viewBinding().loadingProgressBar.visibility = View.GONE
                    }

                    is Resource.Empty -> {
                       /* viewBinding().loadingProgressBar.visibility = View.GONE*/
                    }

                    else -> {
                       /* viewBinding().loadingProgressBar.visibility = View.GONE*/
                    }
                }

            }
        }
    }
}