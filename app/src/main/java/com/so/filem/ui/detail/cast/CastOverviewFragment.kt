package com.so.filem.ui.detail.cast

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.so.filem.R
import com.so.filem.databinding.FragmentCastOverviewBinding
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.utils.parcelable
import com.so.filem.domain.utils.setResizableText
import com.so.filem.ui.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class CastOverviewFragment :
    BaseViewModelFragment<FragmentCastOverviewBinding, DetailCastViewModel>(
        FragmentCastOverviewBinding::inflate
    ) {

    override val viewModel: DetailCastViewModel by viewModels()

    override fun observeData() {
        super.observeData()
        val castDetail = arguments?.parcelable<CastDetails>("castDetail")
        Timber.tag("overview").d(castDetail.toString())
        if (castDetail != null) {
            loadCastView(castDetail)
            setupMediaRv(castDetail)
        }
    }

    private fun setupMediaRv(castDetails: CastDetails) {
        viewBinding().rvCastMedia.setHasFixedSize(true)
        viewBinding().rvCastMedia.layoutManager = LinearLayoutManager(requireContext())

        prepareDataMedia(viewBinding(), castDetails)
    }

    private fun prepareDataMedia(
        viewBinding: FragmentCastOverviewBinding,
        castDetails: CastDetails
    ) {
        val mediaType = castDetails.combinedCredits
        val movieItem = mediaType.filter { it.mediaType == "movie" }
        val tvItem = mediaType.filter { it.mediaType == "tv" }

        val parentItemList = mutableListOf<MediaContent>()

        val parentContentMovie = MediaContent(R.drawable.ic_movie_off_white, "Movies", movieItem)

        val parentContentTv = MediaContent(R.drawable.ic_tv_off_white, "TV show", tvItem)

        parentItemList.add(parentContentMovie)
        parentItemList.add(parentContentTv)
        val adapter = MediaParentAdapter(parentItemList)
        viewBinding.rvCastMedia.adapter = adapter

    }

    private fun loadCastView(itemCast: CastDetails) {
        viewBinding().apply {
            val bio = itemCast.biography
            val name = itemCast.name
            if (!bio.isNullOrEmpty()) {
                tvCastBio.setResizableText(bio, 4, true)
            } else {
                tvCastBio.text = getString(R.string.en_text_no_bio, name)
            }
        }
    }
}