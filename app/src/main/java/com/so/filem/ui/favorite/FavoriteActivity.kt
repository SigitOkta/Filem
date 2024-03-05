package com.so.filem.ui.favorite

import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.databinding.ActivityFavoriteBinding
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.TvShow
import com.so.filem.ui.detail.movie.DetailMovieActivity
import com.so.filem.ui.detail.tv.DetailTvShowActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FavoriteActivity :
    BaseViewModelActivity<ActivityFavoriteBinding, FavoriteViewModel>(ActivityFavoriteBinding::inflate) {
    override val viewModel: FavoriteViewModel by viewModels()

    private var currentTitle: String = "Favorite Movie"
    private val recyclerView: RecyclerView by lazy {
        binding.rvGridFav
    }

    override fun initView() {
        super.initView()
        setupToolbar()
        setupRecyclerView()
        initData(currentTitle)
        observeDataMovie()
    }

    private fun setupToolbar() {
        binding.toolbarFav.title = currentTitle
        binding.toolbarFav.overflowIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_filter)
        binding.toolbarFav.navigationIcon =
            ContextCompat.getDrawable(this, R.drawable.ic_arrow_back)
        binding.toolbarFav.inflateMenu(R.menu.menu_favorite)
        binding.toolbarFav.setOnMenuItemClickListener { item ->
            val id = item.itemId
            val title = item.title
            binding.toolbarFav.title = title.toString()
            if (id == R.id.menu_favorite_movie) {
                initData(title.toString())
                observeDataMovie()
                return@setOnMenuItemClickListener true
            } else if (id == R.id.menu_favorite_tv) {
                initData(title.toString())
                observeDataTv()
                return@setOnMenuItemClickListener true
            }
            false // Handle other items or default behavior
        }
        binding.toolbarFav.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }

    private fun initData(currentTitle: String) {
        viewModel.setFilter(currentTitle)
    }

    private fun observeDataMovie() {
        lifecycleScope.launch {
            viewModel.movies.collect { movies ->
                val emptyView = binding.tvNoFavorite
                if (movies.isNullOrEmpty()) {
                    // Tampilkan empty layout
                    emptyView.text = "No Favorite Movie"
                    emptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    // Tampilkan RecyclerView
                    emptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    val adapter = FavoriteAdapter(object : FavoriteRecyclerBindingInterface<Movie> {
                        override fun bindData(item: Movie, binding: ItemPosterMovieGridBinding) {
                            // Implement your data binding logic here
                            binding.apply {
                                ivPoster.load(item.posterUrl) {
                                    crossfade(true)
                                    placeholder(R.drawable.ic_placeholder_poster)
                                }
                                tvPoster.text = item.title
                                cdGridPoster.setOnClickListener {
                                    DetailMovieActivity.startActivity(
                                        this@FavoriteActivity,
                                        item.id
                                    )
                                }
                            }

                        }
                    })
                    recyclerView.adapter = adapter
                    adapter.updateDataSet(movies)
                }
            }
        }

    }

    private fun observeDataTv() {
        lifecycleScope.launch {
            viewModel.tvs.collect { tvs ->
                val emptyView = binding.tvNoFavorite
                Timber.tag("fragFav2").d(tvs.toString())
                if (tvs.isNullOrEmpty()) {
                    // Tampilkan empty layout
                    emptyView.text = "No Favorite Tv"
                    emptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    // Tampilkan RecyclerView
                    emptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    // Inisialisasi dan atur adapter RecyclerView Anda di sini
                    val adapter =
                        FavoriteAdapter(object : FavoriteRecyclerBindingInterface<TvShow> {
                            override fun bindData(
                                item: TvShow,
                                binding: ItemPosterMovieGridBinding
                            ) {
                                // Implement your data binding logic here
                                binding.apply {
                                    ivPoster.load(item.posterUrl) {
                                        crossfade(true)
                                        placeholder(R.drawable.ic_placeholder_poster)
                                    }
                                    tvPoster.text = item.name
                                    cdGridPoster.setOnClickListener {
                                        DetailTvShowActivity.startActivity(
                                            this@FavoriteActivity,
                                            item.id
                                        )
                                    }
                                }

                            }
                        })
                    recyclerView.adapter = adapter
                    adapter.updateDataSet(tvs)
                }
            }
        }
    }
}