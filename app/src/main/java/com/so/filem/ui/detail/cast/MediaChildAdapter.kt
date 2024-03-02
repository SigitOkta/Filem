/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterBinding
import com.so.filem.domain.model.MediaItem
import com.so.filem.domain.utils.Constants

class MediaChildAdapter(private val childList: List<MediaItem>) :
    RecyclerView.Adapter<MediaChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItem: MediaItem) {
            val posterUrl = if (childItem.posterPath != null) Constants.POSTER_URL + childItem.posterPath else R.drawable.ic_placeholder_poster
            binding.ivPoster.load(posterUrl){
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            if (childItem.character.isNullOrEmpty()){
                binding.tvPoster.text = "Cast"
            } else {
                binding.tvPoster.text = childItem.character
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding =
            ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(childList[position])
    }

    override fun getItemCount(): Int {
        return childList.size
    }
}