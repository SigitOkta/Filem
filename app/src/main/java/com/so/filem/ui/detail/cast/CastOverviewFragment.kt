package com.so.filem.ui.detail.cast

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.so.filem.R
import com.so.filem.databinding.FragmentCastOverviewBinding
import com.so.filem.databinding.FragmentFavoriteBinding
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.utils.parcelable
import com.so.filem.domain.utils.setResizableText
import com.so.filem.ui.adapter.FavoriteAdapter
import com.so.filem.ui.base.BaseViewModelFragment
import com.so.filem.ui.favorite.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber


@AndroidEntryPoint
class CastOverviewFragment : BaseViewModelFragment<FragmentCastOverviewBinding, DetailCastViewModel>(
    FragmentCastOverviewBinding::inflate
) {

    override val viewModel: DetailCastViewModel by viewModels()

    override fun observeData() {
        super.observeData()
        CastOverviewFragment()
        val castDetail = arguments?.parcelable<CastDetails>("castDetail")
        Timber.tag("overview").d(castDetail.toString())
        if (castDetail != null) {
            loadCastView(castDetail)
        }
    }

    private fun loadCastView(itemCast: CastDetails) {
        viewBinding().apply {
            val bio = itemCast.biography
            val name = itemCast.name
            if (!bio.isNullOrEmpty()){
                tvCastBio.setResizableText(bio,4, true)
            } else {
                tvCastBio.text = getString(R.string.en_text_no_bio, name)
            }
        }
    }
}