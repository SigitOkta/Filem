package com.so.filem.ui.favorite

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.FragmentFavoriteBinding
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.TvShow
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.detail.movie.DetailMovieActivity
import com.so.filem.ui.detail.tv.DetailTvShowActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class FavoriteFragment : BaseViewModelFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate
) {
    override val viewModel: FavoriteViewModel by viewModels()

    private val recyclerView: RecyclerView by lazy {
        viewBinding().rvGridFav
    }

    private var currentTitle: String = "Favorite Movie"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as AppCompatActivity?)?.setSupportActionBar(viewBinding().toolbarFav)
        (activity as AppCompatActivity).supportActionBar?.title = currentTitle
        viewBinding().toolbarFav.overflowIcon =
            ContextCompat.getDrawable(requireActivity(), R.drawable.ic_filter)
        setupMenu()
    }


    override fun initView() {
        super.initView()
        setupRecyclerView()
        initData(currentTitle)
        observeDataMovie()
    }



    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_favorite, menu)
            }

            override fun onMenuItemSelected(item: MenuItem): Boolean {
                return when (item.itemId) {
                    R.id.menu_favorite_movie -> {
                        currentTitle = "Favorite Movie"
                        setActionBarTitle(currentTitle)
                        initData(currentTitle)
                        observeDataMovie()
                        true
                    }

                    R.id.menu_favorite_tv -> {
                        currentTitle = "Favorite Tv"
                        setActionBarTitle(currentTitle)
                        initData(currentTitle)
                        observeDataTv()
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun initData(currentTitle: String) {
        viewModel.setFilter(currentTitle)
        Timber.tag("fragFav1").d(currentTitle)
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun observeDataMovie() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.movies.collect { movies ->
                val emptyView = viewBinding().tvNoFavorite
                Timber.tag("fragFav2").d(movies.toString())
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
                            binding.apply{
                                ivPoster.load(item.posterUrl) {
                                    crossfade(true)
                                    placeholder(R.drawable.ic_placeholder_poster)
                                }
                                tvPoster.text = item.title
                                cdGridPoster.setOnClickListener {
                                    DetailMovieActivity.startActivity(requireContext(),item.id)
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

    private fun observeDataTv(){
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.tvs.collect{ tvs ->
                val emptyView = viewBinding().tvNoFavorite
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
                    val adapter = FavoriteAdapter(object : FavoriteRecyclerBindingInterface<TvShow> {
                        override fun bindData(item: TvShow, binding: ItemPosterMovieGridBinding) {
                            // Implement your data binding logic here
                            binding.apply{
                                ivPoster.load(item.posterUrl) {
                                    crossfade(true)
                                    placeholder(R.drawable.ic_placeholder_poster)
                                }
                                tvPoster.text = item.name
                                cdGridPoster.setOnClickListener {
                                    DetailTvShowActivity.startActivity(requireContext(),item.id)
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



    private fun setActionBarTitle(title: String) {
        (activity as AppCompatActivity).supportActionBar?.title = title
    }
}