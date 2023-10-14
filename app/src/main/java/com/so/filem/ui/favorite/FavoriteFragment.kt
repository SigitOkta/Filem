package com.so.filem.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.databinding.FragmentFavoriteBinding
import com.so.filem.ui.adapter.FavoriteAdapter
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseViewModelFragment<FragmentFavoriteBinding, FavoriteViewModel>(
    FragmentFavoriteBinding::inflate
) {
    override val viewModel: FavoriteViewModel by viewModels()

    private val recyclerView: RecyclerView by lazy {
        viewBinding().rvGridFav
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity?)?.supportActionBar?.show()
        (activity as AppCompatActivity?)?.setSupportActionBar(viewBinding().toolbarFav)
        (activity as AppCompatActivity).supportActionBar?.title = "Favorite"
    }

    override fun initView() {
        super.initView()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.movies.collect { movies ->
                val emptyView = viewBinding().tvNoFavorite
                if (movies.isNullOrEmpty()) {
                    // Tampilkan empty layout
                    emptyView.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                } else {
                    // Tampilkan RecyclerView
                    emptyView.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE

                    // Inisialisasi dan atur adapter RecyclerView Anda di sini
                    val adapter = FavoriteAdapter(movies)
                    recyclerView.adapter = adapter
                }
            }
        }
    }
}