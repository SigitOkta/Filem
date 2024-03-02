/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.so.filem.R
import com.so.filem.base.BaseActivity
import com.so.filem.databinding.ActivityMainBinding
import com.so.filem.ui.custom.FabManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private var isExpanded = false
    private val fabManager: FabManager by lazy { FabManager(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNav.setupWithNavController(navController)
        binding.mainFabBtn.setOnClickListener {

            if (isExpanded) {
                fabManager.shrinkFab(binding)
                isExpanded = !isExpanded
            } else {
                fabManager.expandFab(binding)
                isExpanded = !isExpanded
            }

        }

    }


    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {

        if (ev?.action == MotionEvent.ACTION_DOWN) {
            if (isExpanded) {
                val outRect = Rect()
                binding.fabConstraint.getGlobalVisibleRect(outRect)
                if (!outRect.contains(ev.rawX.toInt(), ev.rawY.toInt())) {
                    fabManager.shrinkFab(binding)
                    isExpanded = !isExpanded
                    return dispatchTouchEvent(ev)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onBackPressed() {

        if (isExpanded) {
            fabManager.shrinkFab(binding)
            isExpanded = !isExpanded
        } else {
            super.onBackPressed()

        }
    }
}