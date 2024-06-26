/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

open class BaseFragment<B : ViewBinding>(
    val bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B
) : Fragment() {

    private lateinit var binding: B
    fun viewBinding(): B = binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = bindingFactory(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        initView()
    }

    open fun initView() {

    }

    open fun observeData() {

    }

}

abstract class BaseViewModelFragment<B : ViewBinding, VM : ViewModel>(bindingFactory: (LayoutInflater, ViewGroup?, Boolean) -> B) :
    BaseFragment<B>(bindingFactory) {

    abstract val viewModel: VM
}