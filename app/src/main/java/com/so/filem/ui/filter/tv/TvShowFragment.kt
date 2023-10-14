package com.so.filem.ui.filter.tv

import androidx.fragment.app.viewModels
import com.so.filem.databinding.FragmentTvShowBinding
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowFragment :
    BaseViewModelFragment<FragmentTvShowBinding, TvShowViewModel>(FragmentTvShowBinding::inflate) {
    override val viewModel: TvShowViewModel by viewModels()

}