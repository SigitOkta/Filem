package com.so.filem.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.so.filem.R
import com.so.filem.databinding.ActivityMainBinding
import com.so.filem.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }
}