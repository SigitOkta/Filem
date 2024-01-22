package com.so.filem.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentSearchBinding
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.adapter.LoadingStateAdapter
import com.so.filem.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseViewModelFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels()
    private val searchAdapter by lazy {
        SearchListAdapter(
            viewBinding().includeSearchBar.spinner.selectedItemPosition
        )
    }

    override fun initView() {
        super.initView()
        (activity as AppCompatActivity).supportActionBar?.hide()
        setupRecyclerView()
        setupSpinner()
        inputSearch()
        observeLoadState()
    }

    private fun observeLoadState() {
        searchAdapter.addLoadStateListener { loadState ->
            viewBinding().loadingProgressBar.isVisible =
                loadState.source.refresh is LoadState.Loading
            viewBinding().includeRecyclerView.root.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            viewBinding().tvErrorHome.isVisible = loadState.source.refresh is LoadState.Error

            // search failed to return any matching results
            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                searchAdapter.itemCount < 1
            ) {
                viewBinding().includeRecyclerView.root.isVisible = false
                viewBinding().tvErrorHome.isVisible = true
                    .also {
                        viewBinding().tvErrorHome.text = getString(R.string.en_text_not_found)
                    }
            }

            val error = when {
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }

            if (error != null) {
                viewBinding().tvErrorHome.text = error.error.localizedMessage
            }
        }
    }

    private fun setupSpinner() {
        val spinnerValues = listOf("Movie", "TV", "Person")
        val spinner = viewBinding().includeSearchBar.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.item_custom_spinner, spinnerValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?, position: Int, id: Long
            ) {
                fetchData()
                searchAdapter.updateType(position)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                fetchData()
            }
        }
    }

    private fun fetchData() {
        val query = viewBinding().includeSearchBar.svMovie.query.toString()
        val selectedSpinnerItem = viewBinding().includeSearchBar.spinner.selectedItemPosition
        Timber.tag("Search, SearchFragment").d("$query, $selectedSpinnerItem")
        viewModel.searchMovies(query, selectedSpinnerItem)
    }

    private fun inputSearch() {
        val searchView = viewBinding().includeSearchBar.svMovie
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewBinding().includeRecyclerView.rvSearchItem.scrollToPosition(0)
                    fetchData()
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        searchView.setOnCloseListener {
            Timber.tag("search-init-query").d("click")
            viewBinding().tvErrorHome.isVisible = false
            viewBinding().includeRecyclerView.root.visibility = View.GONE
            true
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().includeRecyclerView.rvSearchItem
        recyclerView.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = LoadingStateAdapter {
                searchAdapter.retry()
            },
            footer = LoadingStateAdapter {
                searchAdapter.retry()
            }
        )
        /* searchAdapter.addLoadStateListener { loadState ->
             if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && searchAdapter.itemCount < 1) {
                 showError()
                 setErrorMessage(getText(R.string.text_empty_data).toString())
             } else {
                 viewBinding().tvErrorHome.isVisible = false
                 viewBinding().includeRecyclerView.root.isVisible = true
             }
         }*/
    }

    override fun observeData() {
        super.observeData()
        viewModel.movies.observe(viewLifecycleOwner) { result ->
            searchAdapter.submitData(viewLifecycleOwner.lifecycle,result)
        }

    }

    private fun setErrorMessage(msg: String) {
        viewBinding().tvErrorHome.text = msg
    }

    private fun showError() {
        viewBinding().apply {
            includeRecyclerView.rvSearchItem.isVisible = false
            loadingProgressBar.isVisible = false
            tvErrorHome.isVisible = true
        }
    }

    private fun showLoading() {
        viewBinding().apply {
            includeRecyclerView.root.isVisible = false
            tvErrorHome.isVisible = false
            loadingProgressBar.isVisible = true
        }
    }
}