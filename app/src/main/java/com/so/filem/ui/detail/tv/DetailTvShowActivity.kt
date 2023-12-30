package com.so.filem.ui.detail.tv

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailTvShowBinding
import com.so.filem.domain.model.TvDetails
import com.so.filem.domain.utils.Resource
import com.so.filem.domain.utils.setResizableText
import com.so.filem.ui.adapter.GenreAdapter
import com.so.filem.base.BaseViewModelActivity
import com.so.filem.domain.model.MediaType
import com.so.filem.ui.custom.Converter
import com.so.filem.ui.custom.LoadingDialog
import com.so.filem.ui.detail.movie.DetailMovieActivity
import com.so.filem.ui.thread.ThreadActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class DetailTvShowActivity :
    BaseViewModelActivity<ActivityDetailTvShowBinding, DetailTvViewModel>(
        ActivityDetailTvShowBinding::inflate
    ) {
    override val viewModel: DetailTvViewModel by viewModels()

    private lateinit var bundle: Bundle
    private lateinit var viewPager2: ViewPager2

    companion object {
        const val EXTRAS_ID = "EXTRAS_ID"
        fun startActivity(context: Context, id: Long) {
            context.startActivity(Intent(context, DetailTvShowActivity::class.java).apply {
                Timber.tag("co").d(id.toString())
                putExtra(EXTRAS_ID, id)
            })
        }

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_detail_tv_1,
            R.string.tab_detail_tv_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        viewPager2 = binding.vpDetailTvShow
        bundle = Bundle()
        binding.includeHeaderPoster.ivFavorite.setOnClickListener {
            viewModel.onFavoriteClicked()
        }
        binding.btnThread.setOnClickListener {
            val id = intent.getLongExtra(EXTRAS_ID, 0)
            ThreadActivity.startActivity(
                this@DetailTvShowActivity,
                id.toString(),
                MediaType.TV_SHOW.ordinal
            )
        }
    }

    override fun initView() {
        super.initView()
        val movieId = intent.getLongExtra(DetailMovieActivity.EXTRAS_ID, 0)
        Timber.tag("activity").d(movieId.toString())
        viewModel.getMovieId(movieId)
        binding.includeHeaderPoster.ivArrowBack.setOnClickListener {
            finish()
        }
    }

    private fun setupTabLayout(tv: TvDetails) {
        val sectionsPagerAdapter = DetailTvSectionPagerAdapter(this, tv)
        viewPager2.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tlCast
        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        viewPager2.isUserInputEnabled = false
        supportActionBar?.elevation = 0f
    }

    private fun loadData(data: TvDetails) {
        binding.includeHeaderPoster.apply {
            //poster
            ivPosterDetail.load(data.tvShow.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            //background_poster
            ivPosterBackground.load(data.tvShow.backdropUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            //title
            tvTitle.text = data.tvShow.name

            //rating
            tvRating.text = data.tvShow.vote_average?.let { Converter.roundOffDecimal(it) }
        }
        binding.apply {
            //overview
            if (data.tvShow.overview != null) {
                tvDesciption.setResizableText(data.tvShow.overview, 4, true)
            } else {
                tvDesciption.text = "No overview"
            }
            //genre
            val flexboxLayoutManager = FlexboxLayoutManager(this@DetailTvShowActivity)
            flexboxLayoutManager.apply {
                flexDirection = FlexDirection.ROW
                flexWrap = FlexWrap.WRAP
            }
            includeHeaderPoster.rvGenre.layoutManager = flexboxLayoutManager
            val genreAdapter = GenreAdapter(data.genres)
            includeHeaderPoster.rvGenre.adapter = genreAdapter
        }
    }


    override fun observeData() {
        super.observeData()
        lifecycleScope.launch {
            viewModel.tvDetails.collectLatest {
                when (it) {
                    is Resource.Empty -> {

                    }

                    is Resource.Error -> {
                        LoadingDialog.hideLoading()
                    }

                    is Resource.Loading -> LoadingDialog.startLoading(this@DetailTvShowActivity)
                    is Resource.Success -> {
                        LoadingDialog.hideLoading()
                        it.payload?.let { tv ->
                            loadData(tv)
                            setupTabLayout(tv)
                        }

                    }

                    else -> false
                }
            }
        }
        lifecycleScope.launch() {
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