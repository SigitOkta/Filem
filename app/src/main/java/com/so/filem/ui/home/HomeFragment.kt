/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.home

import android.content.Intent
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.so.filem.R
import com.so.filem.databinding.FragmentHomeBinding
import com.so.filem.ui.auth.AuthActivity
import com.so.filem.base.BaseViewModelFragment
import com.so.filem.domain.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, HomeViewModel>(
    FragmentHomeBinding::inflate
) {
    private val TAG = HomeFragment::class.java.simpleName
    override val viewModel: HomeViewModel by viewModels()




    override fun observeData() {
        super.observeData()
        setupRv()
        viewModel.parentData.observe(this){ homeData->
            when(homeData){
                is Resource.Empty -> {
                    showError()
                    setErrorMessage(getText(R.string.text_empty_data).toString())

                }
                is Resource.Error -> {
                    viewBinding().apply {
                        rvHome.isVisible = false
                        pbHome.isVisible = false
                        tvErrorHome.isVisible = true
                    }
                }
                is Resource.Loading -> {
                    showLoading()
                }
                is Resource.Success -> {
                    showData(homeData.payload)
                }
            }

        }
        viewModel.currentUserLiveData.observe(this) { user ->
            if (user != null) {
                viewBinding().includeToolbar.txtUsername.text = user.displayName
            }
        }
    }

    private fun showData(items: List<HomeItem>?) {
        viewBinding().apply {
            rvHome.isVisible = true
            tvErrorHome.isVisible = false
            pbHome.isVisible = false
        }
        if (items != null) {
            prepareDataHome(viewBinding(), items)
        }
    }

    private fun showLoading() {
        viewBinding().apply {
            rvHome.isVisible = false
            tvErrorHome.isVisible = false
            pbHome.isVisible = true
        }
    }

    private fun setErrorMessage(msg: String) {
        viewBinding().tvErrorHome.text = msg
    }

    private fun showError() {
        viewBinding().apply {
            rvHome.isVisible = false
            pbHome.isVisible = false
            tvErrorHome.isVisible = true
        }
    }

    override fun initView() {
        super.initView()
        viewModel.getParentData()
        viewModel.getCurrentUser()
    }

    private fun setupRv() {
        viewBinding().rvHome.setHasFixedSize(true)
        viewBinding().rvHome.layoutManager = LinearLayoutManager(
            requireContext()
        )

    }

    private fun prepareDataHome(viewBinding: FragmentHomeBinding, homeItem: List<HomeItem>) {
        val adapter = HomeParentAdapter(homeItem, requireActivity())
        viewBinding.rvHome.adapter = adapter
    }
}