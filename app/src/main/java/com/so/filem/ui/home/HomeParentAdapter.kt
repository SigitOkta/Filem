package com.so.filem.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.so.filem.databinding.ItemHomeTrendingBinding

class HomeParentAdapter(private val homeItemList: List<HomeContent>, private val fragmentActivity: FragmentActivity) :
    RecyclerView.Adapter<HomeParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(private val binding: ItemHomeTrendingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parentItem: HomeContent) {

            binding.ivTrending.setImageResource(parentItem.image)
            binding.tvHomeTitle.text = parentItem.title
            val titles = parentItem.titleTab
            val sectionsPagerAdapter = HomeSectionsPagerAdapter( fragmentActivity, titles)
            val viewPager = binding.vpHome
            viewPager.adapter = sectionsPagerAdapter
            viewPager.isUserInputEnabled = false
            val tabs: TabLayout = binding.tlHome
            TabLayoutMediator(tabs, viewPager) { tab, position ->
                tab.text = titles[position]
            }.attach()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding =
            ItemHomeTrendingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return homeItemList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(homeItemList[position])
    }

}