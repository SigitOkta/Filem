/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.appintro.AppIntro2
import com.github.appintro.AppIntroFragment
import com.so.filem.R
import com.so.filem.data.local.storepref.UserPreference
import com.so.filem.ui.auth.AuthActivity

class OnBoardingActivity : AppIntro2(){
    companion object{
        private const val TAG = "OnBoardingActivity"
    }

    private val preference: UserPreference by lazy{
        UserPreference(this@OnBoardingActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setupSliderFragment()
    }

    private fun setupSliderFragment() {
        isSkipButtonEnabled = false
        setIndicatorColor(
            selectedIndicatorColor = ContextCompat.getColor(this, R.color.off_white_400),
            unselectedIndicatorColor = ContextCompat.getColor(this, R.color.light_blue),
        )
        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.text_onboard_desc_1),
                imageDrawable = R.drawable.ic_onboard_image_1,
                descriptionTypefaceFontRes = R.font.inter_medium,
                backgroundColorRes = R.color.dark_blue_600,
                descriptionColorRes = R.color.off_white_400
            )
        )

        addSlide(
            AppIntroFragment.createInstance(
                description = getString(R.string.text_onboard_desc_2),
                imageDrawable = R.drawable.ic_onboard_image_2,
                descriptionTypefaceFontRes = R.font.inter_medium,
                backgroundColorRes = R.color.dark_blue_600,
                descriptionColorRes = R.color.off_white_400
            )
        )
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        preference.setSkipIntro(true)
        Intent(this@OnBoardingActivity, AuthActivity::class.java).also {
            startActivity(it)
        }
    }
}