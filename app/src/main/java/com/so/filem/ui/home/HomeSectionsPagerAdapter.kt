package com.so.filem.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class HomeSectionsPagerAdapter(
    context: FragmentActivity,
    private val titles: List<String>,
    private val media: String
) : FragmentStateAdapter(context) {
    override fun createFragment(position: Int): Fragment {
        val fragment = HomeChildFragment()
        fragment.arguments = Bundle().apply {
            // Ganti dengan judul tab yang sesuai
            putString(HomeChildFragment.ARG_SECTION_TITLE, titles[position])
            putString(HomeChildFragment.ARG_SECTION_MEDIA_TYPE, media)
        }
        return fragment
    }

    override fun getItemCount(): Int {
        return titles.size
    }
}



