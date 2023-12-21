package com.so.filem.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.so.filem.data.local.storepref.UserPreference
import com.so.filem.databinding.ActivitySplashScreenBinding
import com.so.filem.ui.MainActivity
import com.so.filem.ui.base.BaseActivity
import com.so.filem.ui.onboarding.OnBoardingActivity

class SplashScreenActivity :
    BaseActivity<ActivitySplashScreenBinding>(ActivitySplashScreenBinding::inflate) {


    private val preference: UserPreference by lazy {
        UserPreference(this@SplashScreenActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }


    private fun initView() {
        supportActionBar?.hide()
    }

    private fun observeData() {
        if (preference.isSkipIntro()) {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }, 3000)

        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                })
            }, 3000)
        }
    }

}