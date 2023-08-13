package com.so.filem.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailMovieBinding
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.utils.Constants
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.base.BaseViewModelActivity
import com.so.filem.ui.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.annotation.meta.When

@AndroidEntryPoint
class DetailMovieActivity :
    BaseViewModelActivity<ActivityDetailMovieBinding, DetailMovieViewModel>(
        ActivityDetailMovieBinding::inflate
    ) {

    override val viewModel: DetailMovieViewModel by viewModels()

    companion object {
        const val EXTRAS_ID = "EXTRAS_ID"
        fun startActivity(context: Context, id: Int) {
            context.startActivity(Intent(context, DetailMovieActivity::class.java).apply {
                Timber.tag("co").d(id.toString())
                putExtra(EXTRAS_ID, id)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        initView()
        observeData()
}

    private fun initView() {
        val movieId = intent.getIntExtra(EXTRAS_ID,0)
        Timber.tag("activity").d(movieId.toString())
        viewModel.getMovieDetails(movieId)
    }

    private fun loadData(movie: Movie) {
        val posterUrl =
            if (movie.poster_path != null) Constants.POSTER_URL + movie.poster_path else null
        val backdropUrl =
            if (movie.backdrop_path != null) Constants.BACKDROP_URL + movie.backdrop_path else null
        binding.apply {
            ivPosterDetail.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            ivPosterBackground.load(backdropUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            tvTitle.text = movie.title
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.selectedMovie.collectLatest {
                when (it) {
                    is Resource.Empty -> {

                    }

                    is Resource.Error -> {
                        LoadingDialog.hideLoading()
                    }

                    is Resource.Loading -> LoadingDialog.startLoading(this@DetailMovieActivity)
                    is Resource.Success -> {
                        LoadingDialog.hideLoading()
                        it.payload?.let { movie ->
                            loadData(movie)
                        }
                    }

                    else -> false
                }
            }
        }
    }
}