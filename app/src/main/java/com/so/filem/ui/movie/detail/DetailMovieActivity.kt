package com.so.filem.ui.movie.detail

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailMovieBinding
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.MovieDetails
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
        fun startActivity(context: Context, id: Long) {
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
        binding.ivFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
}

    private fun initView() {
        val movieId = intent.getLongExtra(EXTRAS_ID,0)
        Timber.tag("activity").d(movieId.toString())
        viewModel.getMovieId(movieId)
    }

    private fun loadData(data: MovieDetails) {

        binding.apply {
            ivPosterDetail.load(data.movie.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            ivPosterBackground.load(data.movie.backdropUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            tvTitle.text = data.movie.title
            if (data.movie.overview != null) {
                tvDesciption.text = data.movie.overview
            } else {
                tvDesciption.text = "No overview"
            }
            /*if (data.runtime != 0) {
                tvDuration.text = data.runtime?.let { Converter.fromMinutesToHHmm(it) }
            }*/
            tvRating.text = data.movie.vote_average?.let { Converter.roundOffDecimal(it) }


        }
    }


    private fun observeData() {
        lifecycleScope.launch {
            viewModel.movieDetails.collectLatest {
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
        lifecycleScope.launch(){
            viewModel.isFavorite.collectLatest {
                updateFavoriteUi(it)
                Timber.tag("viewModel-detail").d(it.toString())
            }
        }
    }

    private fun updateFavoriteUi(isFavorite: Boolean?) {
        //show icon if true /
        when (isFavorite) {
            true -> {
                favoriteIcon(R.drawable.ic_favorite)
                Timber.d("Film ini adalah favorit")
            }
            else -> {
                favoriteIcon(R.drawable.ic_favorite_border)
                Timber.d("Film ini adalah unfavorite")
            }
        }



    }


    private fun favoriteIcon(icFavorite: Int) {
        val ivFavorite = binding.ivFavorite
        ivFavorite.setImageResource(
            icFavorite
        )
    }
}