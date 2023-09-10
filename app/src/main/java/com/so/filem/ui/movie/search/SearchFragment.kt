package com.so.filem.ui.movie.search

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.TypedValue

import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.so.filem.databinding.FragmentSearchBinding
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.adapter.DiscoverAdapter
import com.so.filem.ui.adapter.LoadingStateAdapter
import com.so.filem.ui.adapter.SearchListAdapter
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Math.abs

@AndroidEntryPoint
class SearchFragment : BaseViewModelFragment<FragmentSearchBinding, SearchViewModel>(
    FragmentSearchBinding::inflate
) {
    override val viewModel: SearchViewModel by viewModels()

    private val searchAdapter by lazy { SearchListAdapter() }
    private var searchJob: Job? = null
    private lateinit var handler : Handler
    private lateinit var viewPager2: ViewPager2
    private lateinit var discoverAdapter: DiscoverAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
        inputSearch()
        setupViewPager()
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setupViewPager() {
        setUpTransformer()

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable , 2000)
            }
        })
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(25))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.75f + r * 0.14f


          /*  val curvature = 10f // Atur tingkat kurva sesuai keinginan Anda
            val angle = position * curvature

            // Terapkan efek kurva pada halaman
            page.pivotX = page.width / 2f
            page.pivotY = page.height.toFloat()
            page.rotation = angle*/

            // Cek apakah posisi adalah -1 atau 1
            if (position == -1f || position == 1f) {
                //page.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.transparent))
                page.alpha = 0.3f // Ubah tingkat transparansi sesuai keinginan Anda
            } else {
                // Reset latar belakang dan transparansi jika tidak pada posisi -1 atau 1
                //page.setBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white)) // Ubah warna latar belakang sesuai keinginan Anda
                page.alpha = 1.0f
            }

         /*   val rotation = if (position < 0) {
                15f * position // Poster 3 miring ke kiri saat digulirkan ke samping kiri
            } else if (position > 0) {
                15f * position // Poster 1 miring ke kanan saat digulirkan ke samping kanan
            } else {
                0f // Poster 2 tidak dimiringkan saat aktif
            }
            page.rotation = rotation*/
        }

        viewPager2.setPageTransformer(transformer)
    }


    private fun inputSearch() {
        val searchView = viewBinding().includeSearchBar.svMovie
        searchView.isIconified = false
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.updateQuery(it)
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
            viewBinding().includeViewPager.root.visibility = View.VISIBLE
            viewBinding().includeRecyclerView.root.visibility = View.GONE
            true
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding().includeRecyclerView.rvSearchItem
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = searchAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                searchAdapter.retry()
            }
        )
        searchAdapter.addLoadStateListener { loadState ->
            val isListEmpty = loadState.refresh is LoadState.Error
            if (isListEmpty) {
                viewBinding().includeNotFound.root.visibility = View.VISIBLE
                viewBinding().includeViewPager.root.visibility = View.GONE
                viewBinding().includeRecyclerView.root.visibility = View.GONE
            } else {
                viewBinding().includeNotFound.root.visibility = View.GONE
                viewBinding().includeViewPager.root.visibility = View.GONE
                viewBinding().includeRecyclerView.root.visibility = View.VISIBLE
            }
        }
    }

    override fun observeData() {
        super.observeData()
        searchJob?.cancel()
        searchJob = lifecycleScope.launch {
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
                                searchAdapter.submitData(lifecycle, it)
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

        lifecycleScope.launch {
            viewModel.discoverMovie.collect{
                viewPager2 = viewBinding().includeViewPager.vpMovieDiscover
                handler = Handler(Looper.myLooper()!!)
                discoverAdapter = DiscoverAdapter(ArrayList(it), viewPager2)
                viewPager2.adapter = discoverAdapter
                viewPager2.offscreenPageLimit = 3
                viewPager2.clipToPadding = false
                viewPager2.clipChildren = false
                viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            }
        }
    }

    override fun onDestroy() {
        searchJob?.cancel()
        super.onDestroy()
    }

    override fun onPause() {
        super.onPause()

        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }
}