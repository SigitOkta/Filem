package com.so.filem.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.so.filem.data.local.storepref.UserPreference
import com.so.filem.databinding.ActivitySplashScreenBinding
import com.so.filem.ui.MainActivity
import com.so.filem.ui.auth.AuthActivity
import com.so.filem.ui.base.BaseViewModelActivity
import com.so.filem.ui.onboarding.OnBoardingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SplashScreenActivity :
    BaseViewModelActivity<ActivitySplashScreenBinding, SplashViewModel>(ActivitySplashScreenBinding::inflate) {


    private val preference: UserPreference by lazy {
        UserPreference(this@SplashScreenActivity)
    }
    override val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        observeData()
    }


    private fun initView() {
        supportActionBar?.hide()
        viewModel.getCurrentUser()
    }

    private fun observeData() {
        viewModel.currentUserLiveData.observe(this) { user ->
            if (preference.isSkipIntro()) {
                if (user == null) {
                    lifecycleScope.launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreenActivity, AuthActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }
                } else {
                    lifecycleScope.launch {
                        delay(2000)
                        startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java).apply {
                            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        })
                    }
                }
            } else {
                lifecycleScope.launch {
                    delay(2000)
                    startActivity(Intent(this@SplashScreenActivity, OnBoardingActivity::class.java).apply {
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    })
                }
            }

        }
    }

}