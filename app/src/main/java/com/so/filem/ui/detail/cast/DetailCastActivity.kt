/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.cast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.so.filem.R
import com.so.filem.databinding.ActivityDetailCastBinding
import com.so.filem.ui.adapter.CastImageAdapter
import com.so.filem.base.BaseViewModelActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailCastActivity : BaseViewModelActivity<ActivityDetailCastBinding, DetailCastViewModel>(
    ActivityDetailCastBinding::inflate
) {

    override val viewModel: DetailCastViewModel by viewModels()
    private lateinit var bundle : Bundle
    private lateinit var handler: Handler
    private lateinit var viewPager2: ViewPager2
    private lateinit var castImageAdapter: CastImageAdapter

    companion object {
        const val EXTRAS_ID = "EXTRAS_ID"
        fun startActivity(context: Context, id: Long) {
            context.startActivity(Intent(context, DetailCastActivity::class.java).apply {
                putExtra(EXTRAS_ID, id)
            })
        }
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_cast_1,
            R.string.tab_cast_2,
        )

    }

    private fun setupToolbar(name : String){
        binding.collapsingToolbar.isTitleEnabled = false
        binding.tbCast.title = name
        setSupportActionBar(binding.tbCast)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    // don't forget click listener for back button
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun observeData() {
        super.observeData()
        viewModel.castDetails.observe(this) {
            castImageAdapter = CastImageAdapter(ArrayList(it.images), viewPager2)
            Timber.tag("viewModelCast").d(it.images.toString())
            bundle.putParcelable("castDetail", it)
            Timber.tag("DetailCast").d(bundle.toString())
            viewPager2.adapter = castImageAdapter
            viewPager2.offscreenPageLimit = 3
            viewPager2.clipToPadding = false
            viewPager2.clipChildren = false
            viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setupTabLayout()
            setupToolbar(it.name)
        }
    }

    override fun initView() {
        super.initView()
        viewPager2 = binding.vpCastImage
        handler = Handler(Looper.myLooper()!!)
        bundle = Bundle()
        val castId = intent.getLongExtra(EXTRAS_ID, 0)
        viewModel.setCastId(castId)

        setupViewPagerImage()
    }

    private val runnable = Runnable {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setupViewPagerImage() {
        setUpTransformer()
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(25))
        transformer.addTransformer { page, position ->
            val r = 1 - Math.abs(position)
            page.scaleY = 0.75f + r * 0.14f
            if (position == -1f || position == 1f) {
                page.alpha = 0.3f
            } else {
                page.alpha = 1.0f
            }
        }
        viewPager2.setPageTransformer(transformer)
    }



    private fun setupTabLayout() {
        Timber.tag("DetailCast").d(bundle.toString())
        val sectionsPagerAdapter = CastSectionsPagerAdapter(this, bundle)
        val viewPager = binding.vpCast
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tlCast
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }
}