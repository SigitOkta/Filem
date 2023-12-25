package com.so.filem.ui.detail.cast

import androidx.fragment.app.viewModels
import com.so.filem.R
import com.so.filem.databinding.FragmentCastPersonalInfoBinding
import com.so.filem.domain.model.CastDetails
import com.so.filem.domain.utils.parcelable
import com.so.filem.base.BaseViewModelFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class CastPersonalInfoFragment :  BaseViewModelFragment<FragmentCastPersonalInfoBinding, DetailCastViewModel>(
    FragmentCastPersonalInfoBinding::inflate
) {
    override val viewModel: DetailCastViewModel by viewModels()


    override fun observeData() {
        super.observeData()
        val castDetail = arguments?.parcelable<CastDetails>("castDetail")
        Timber.tag("overview").d(castDetail.toString())
        if (castDetail != null) {
            setupInfoAdapter(castDetail)
        }
    }

    private fun setupInfoAdapter(castInfo: CastDetails) {

        val data = mutableListOf<InfoCastContent>()
        if (castInfo.knownForDepartment != null) {
            data.add(InfoCastContent("Known For", castInfo.knownForDepartment))
        }

        val gender = when(castInfo.gender){
            1 -> getString(R.string.en_text_female)
            2 -> getString(R.string.en_text_male)
            3 -> getString(R.string.en_text_no_binary)
            else -> "-"
        }

        data.add(InfoCastContent("Gender", gender))


        if (castInfo.birthday != null) {
            data.add(InfoCastContent("Birthday", castInfo.birthday))
        }

        if (castInfo.deathDay != null) {
            data.add(InfoCastContent("Day of Death", castInfo.deathDay))
        }

        if (castInfo.placeOfBirth != null) {
            data.add(InfoCastContent("Place of Birth", castInfo.placeOfBirth))
        }
        val alsoKnownAsList = castInfo.alsoKnownAs
        val alsoKnownAsString = alsoKnownAsList.joinToString(", ")

        data.add(InfoCastContent("Also Known As", alsoKnownAsString))

        val adapter = CastInfoAdapter(requireContext(), data)
        viewBinding().lvCastInfo.adapter = adapter
    }


}