package com.so.filem.ui.search

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
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
import kotlinx.coroutines.launch
import timber.log.Timber

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
        setupSpinner()
        setupRecyclerView()
        inputSearch()
    }

    private fun setupSpinner() {
        val spinnerValues = listOf("Movie", "Tv", "Person")
        val spinner = viewBinding().includeSearchBar.spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.item_custom_spinner, spinnerValues)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner.adapter = adapter

        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View?, position: Int, id: Long) {
                fetchData()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
    }

    private fun fetchData() {
        val query = viewBinding().includeSearchBar.svMovie.query.toString()
        val selectedSpinnerItem = viewBinding().includeSearchBar.spinner.selectedItem.toString()
        Timber.tag("Search, SearchFragment").d("$query, $selectedSpinnerItem")
        viewModel.fetchSearch(query, selectedSpinnerItem)
    }

    private fun inputSearch() {
        val searchView = viewBinding().includeSearchBar.svMovie
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    fetchData()
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    searchView.isIconified = false
                    searchView.isIconified = true
                }
                return false
            }
        })

        searchView.setOnCloseListener {
            Timber.tag("search-init-query").d("click")
            viewBinding().includeNotFound.root.visibility = View.GONE
            viewBinding().includeRecyclerView.root.visibility = View.GONE
            true
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().includeRecyclerView.rvSearchItem
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        recyclerView.adapter = searchAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                searchAdapter.retry()
            }
        )
        searchAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.Error
            if (isListEmpty) {
                viewBinding().includeNotFound.root.visibility = View.VISIBLE
                viewBinding().includeRecyclerView.root.visibility = View.GONE
            } else {
                viewBinding().includeNotFound.root.visibility = View.GONE
                viewBinding().includeRecyclerView.root.visibility = View.VISIBLE
            }
        }
    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.searchResults.collectLatest { result ->
                when (result) {
                    //...
                    is Resource.Loading -> {
                        // Menampilkan ProgressBar saat pencarian sedang berlangsung
                        viewBinding().loadingProgressBar.visibility = View.VISIBLE
                    }

                    is Resource.Success -> {
                        // Menampilkan hasil pencarian jika berhasil
                        viewBinding().loadingProgressBar.visibility = View.GONE
                        result.payload?.let { search ->
                            val rv = viewBinding().includeRecyclerView.rvSearchItem
                            rv.adapter = searchAdapter
                            searchAdapter.submitData(search)
                            rv.setHasFixedSize(true)
                        }
                        viewBinding().includeNotFound.root.visibility = View.GONE
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