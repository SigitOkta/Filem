/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterViewpagerBinding
import com.so.filem.domain.model.Movie
import com.so.filem.domain.model.ProfilesItem
import timber.log.Timber

class CastImageAdapter(private val castImage: ArrayList<ProfilesItem>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<CastImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(private val binding: ItemPosterViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProfilesItem){
            binding.ivPosterViewPager.load(item.profileImageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            Timber.tag("CastImageAdapter").d(item.profileImageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemPosterViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(castImage[position])

        if (position == castImage.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return castImage.size
    }

    private val runnable = Runnable {
        castImage.addAll(castImage)
        notifyDataSetChanged()
    }
}