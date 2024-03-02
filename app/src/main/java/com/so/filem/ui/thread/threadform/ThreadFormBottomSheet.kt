/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.thread.threadform

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.so.filem.R
import com.so.filem.base.BaseVMBottomSheetFragment
import com.so.filem.databinding.FragmentThreadFormBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ThreadFormBottomSheet :
    BaseVMBottomSheetFragment<FragmentThreadFormBottomSheetBinding, ThreadFormViewModel>(
        FragmentThreadFormBottomSheetBinding::inflate
    ) {
    override val viewModel: ThreadFormViewModel by viewModels()
    companion object {
        fun newInstance(id: String, mediaType: Int) = ThreadFormBottomSheet().apply {
            arguments = Bundle().apply {
                putString("ID", id)
                putInt("MEDIA_TYPE", mediaType)
            }
        }
    }

    private val id: String by lazy {
        // Ambil data dari `Bundle`
        arguments?.getString("ID") ?: ""
    }

    private val mediaType: Int by lazy {
        arguments?.getInt("MEDIA_TYPE") ?: 0
    }
    override fun initView() {
        super.initView()
        binding.btnCreateThread.setOnClickListener {
            createThread()
        }
    }

    private fun checkFormValidation(): Boolean {
        val title = binding.etThreadTitle.text.toString()
        val body = binding.etThreadBody.text.toString()
        var isFormValid = true
        if (title.isEmpty()) {
            isFormValid = false
            binding.tilThreadTitle.isErrorEnabled = true
            binding.tilThreadTitle.error = getString(R.string.text_error_empty_title)
        } else {
            binding.tilThreadTitle.isErrorEnabled = false
        }
        if (body.isEmpty()) {
            isFormValid = false
            binding.tilThreadBody.isErrorEnabled = true
            binding.tilThreadBody.error = getString(R.string.text_error_empty_body)
        } else {
            binding.tilThreadBody.isErrorEnabled = false
        }

        return isFormValid
    }

    private fun createThread() {
        if (checkFormValidation()) {
            val title = binding.etThreadTitle.text.toString()
            val body = binding.etThreadBody.text.toString()
            viewModel.createThread(id, mediaType, title,body)
            dismiss()
        }
    }
}