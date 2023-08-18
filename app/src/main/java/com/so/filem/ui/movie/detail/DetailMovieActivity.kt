package com.so.filem.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailMovieBinding
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.utils.Constants
import com.so.filem.domain.utils.Resource
import com.so.filem.ui.base.BaseViewModelActivity
import com.so.filem.ui.custom.Converter
import com.so.filem.ui.custom.LoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

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

    private fun loadData(data: MoviePaging) {
        val posterUrl =
            if (data.poster_path != null) Constants.POSTER_URL + data.poster_path else null
        val backdropUrl =
            if (data.backdrop_path != null) Constants.BACKDROP_URL + data.backdrop_path else null
        binding.apply {
            ivPosterDetail.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            ivPosterBackground.load(backdropUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            tvTitle.text = data.title
            if (data.overview != null) {
                tvDesciption.text = data.overview
            } else {
                tvDesciption.text = "No overview"
            }
            /*if (data.runtime != 0) {
                tvDuration.text = data.runtime?.let { Converter.fromMinutesToHHmm(it) }
            }*/
            tvRating.text = data.vote_average?.let { Converter.roundOffDecimal(it) }
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