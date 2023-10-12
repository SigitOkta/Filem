package com.so.filem.ui.detail.cast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class CastSectionsPagerAdapter(activity: AppCompatActivity,  private val bundle: Bundle) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = CastOverviewFragment()
                fragment.arguments = bundle
            }
            1 -> {
                fragment = CastPersonalInfoFragment()
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }
}