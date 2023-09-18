package com.so.filem.ui.detail.cast

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.so.filem.R
import com.so.filem.databinding.FragmentCastOverviewBinding
import com.so.filem.databinding.FragmentCastPersonalInfoBinding
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CastPersonalInfoFragment :  BaseViewModelFragment<FragmentCastPersonalInfoBinding, DetailCastViewModel>(
    FragmentCastPersonalInfoBinding::inflate
) {
    override val viewModel: DetailCastViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}