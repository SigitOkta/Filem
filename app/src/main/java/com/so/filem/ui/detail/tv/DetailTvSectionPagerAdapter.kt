package com.so.filem.ui.detail.tv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.so.filem.domain.model.TvDetails

class DetailTvSectionPagerAdapter(activity: AppCompatActivity, private val tv: TvDetails) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        val fragment = DetailTvShowFragment()
        val args = Bundle()
        when (position) {
            0 ->{
                args.putInt("arg_key", position)
                args.putParcelable("arg_bundle", tv)
            }
            1 -> {
                args.putInt("arg_key", position)
                args.putParcelable("arg_bundle", tv)
            }
        }
        fragment.arguments = args
        return fragment
    }
}