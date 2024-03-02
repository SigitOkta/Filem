/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.movie

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailMovieBinding
import com.so.filem.domain.model.MovieDetails
import com.so.filem.domain.model.Trailer
import com.so.filem.domain.utils.Resource
import com.so.filem.domain.utils.setResizableText
import com.so.filem.ui.adapter.CastAdapter
import com.so.filem.ui.adapter.GenreAdapter
import com.so.filem.ui.adapter.TrailerAdapter
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.domain.model.MediaType
import com.so.filem.ui.custom.Converter
import com.so.filem.ui.custom.LoadingDialog
import com.so.filem.ui.thread.ThreadActivity
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
        binding.includeHeaderPoster.ivFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
        binding.btnThread.setOnClickListener {
            val movieId = intent.getLongExtra(EXTRAS_ID,0)
            ThreadActivity.startActivity(this@DetailMovieActivity,movieId.toString(), MediaType.MOVIE.ordinal)
        }
}

    override fun initView() {
        super.initView()
        val movieId = intent.getLongExtra(EXTRAS_ID,0)
        Timber.tag("activity").d(movieId.toString())
        viewModel.getMovieId(movieId)
        binding.includeHeaderPoster.ivArrowBack.setOnClickListener {
            finish()
        }
    }

    private fun loadData(data: MovieDetails) {
        binding.includeHeaderPoster.apply {
            //poster
            ivPosterDetail.load(data.movie.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            //background_poster
            ivPosterBackground.load(data.movie.backdropUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            //title
            tvTitle.text = data.movie.title
            //runtime
            if (data.movie.runtime != 0) {
                tvDuration.text = data.movie.runtime?.let { Converter.fromMinutesToHHmm(it) }
            }
            //rating
            tvRating.text = data.movie.vote_average?.let { Converter.roundOffDecimal(it) }
        }
        binding.apply {
            //overview
            if (data.movie.overview != null) {
                tvDesciption.setResizableText(data.movie.overview, 4 , true)
            } else {
                tvDesciption.text = "No overview"
            }
            //genre
            val flexboxLayoutManager = FlexboxLayoutManager(this@DetailMovieActivity)
            flexboxLayoutManager.apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            includeHeaderPoster.rvGenre.layoutManager = flexboxLayoutManager
            val genreAdapter = GenreAdapter(data.genres)
            includeHeaderPoster.rvGenre.adapter = genreAdapter

            //cast
            if(data.cast.isEmpty()){
                includeCast.root.visibility = View.GONE
            }else{
                val rvCast = includeCast.rvCast
                rvCast.layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
                val castAdapter = CastAdapter(data.cast)
                rvCast.adapter = castAdapter
            }
            //trailer
            if(data.trailers.isEmpty()){
                includeVideo.root.visibility = View.GONE
            }else{
                val rvTrailer = includeVideo.rvTrailer
                rvTrailer.layoutManager = LinearLayoutManager(this@DetailMovieActivity, LinearLayoutManager.HORIZONTAL, false)
                val trailerAdapter = TrailerAdapter(data.trailers){ trailer ->
                    playVideo(trailer, this@DetailMovieActivity)
                }
                rvTrailer.adapter = trailerAdapter
            }
        }
    }

    private fun playVideo(trailer: Trailer?, context: Context) {
        if (trailer?.key != null) {
            val appIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youTubeAppUrl))
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(trailer.youTubeWebUrl))

            if (appIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(appIntent)
            } else {
                context.startActivity(webIntent)
            }
        }
    }


    override fun observeData() {
        super.observeData()
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
        val ivFavorite = binding.includeHeaderPoster.ivFavorite
        ivFavorite.setImageResource(
            icFavorite
        )
    }
}