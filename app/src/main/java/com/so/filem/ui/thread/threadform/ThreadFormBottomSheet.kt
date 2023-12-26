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
        fun newInstance(data: String) = ThreadFormBottomSheet().apply {
            arguments = Bundle().apply {
                putString("MOVIE_ID", data)
            }
        }
    }

    private val movieId: String by lazy {
        // Ambil data dari `Bundle`
        arguments?.getString("MOVIE_ID") ?: ""
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
            viewModel.createThread(movieId,title,body)
        }
    }
}