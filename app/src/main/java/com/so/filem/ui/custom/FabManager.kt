/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 8:40 PM
 */

package com.so.filem.ui.custom

import android.content.Context
import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat.startActivity
import com.so.filem.R
import com.so.filem.databinding.ActivityMainBinding
import com.so.filem.ui.favorite.FavoriteActivity


class FabManager(context : Context) {
    private val fromBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.from_bottom_fab)
    }
    private val toBottomFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.to_bottom_fab)
    }
    private val rotateClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_clock_wise)
    }
    private val rotateAntiClockWiseFabAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.rotate_anti_clock_wise)
    }
    private val fromBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.from_bottom_anim)
    }
    private val toBottomBgAnim: Animation by lazy {
        AnimationUtils.loadAnimation(context, R.anim.to_bottom_anim)
    }

    fun onFavoriteClicked(context: Context) {
        val intent = Intent(context, FavoriteActivity::class.java)
        startActivity(context,intent,null)
    }

    fun shrinkFab(binding: ActivityMainBinding) {

        binding.transparentBg.startAnimation(toBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateAntiClockWiseFabAnim)
        binding.favFabBtn.startAnimation(toBottomFabAnim)
        binding.forumFabBtn.startAnimation(toBottomFabAnim)
        binding.tvFabFav.startAnimation(toBottomFabAnim)
        binding.tvFabForum.startAnimation(toBottomFabAnim)
    }

    fun expandFab(binding: ActivityMainBinding) {

        binding.transparentBg.startAnimation(fromBottomBgAnim)
        binding.mainFabBtn.startAnimation(rotateClockWiseFabAnim)
        binding.favFabBtn.startAnimation(fromBottomFabAnim)
        binding.forumFabBtn.startAnimation(fromBottomFabAnim)
        binding.tvFabFav.startAnimation(fromBottomFabAnim)
        binding.tvFabForum.startAnimation(fromBottomFabAnim)
    }
}

