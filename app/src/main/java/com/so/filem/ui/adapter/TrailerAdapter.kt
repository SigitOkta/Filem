/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemVideosBinding
import com.so.filem.domain.model.Trailer
import timber.log.Timber

class TrailerAdapter(private val trailers: List<Trailer>, private var itemClick:(Trailer?) -> Unit) : RecyclerView.Adapter<TrailerAdapter.ViewHolder>() {
    
    class ViewHolder(private val binding: ItemVideosBinding, val itemClick: (Trailer?) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Trailer){
            binding.ivPosterTrailer.load(item.youTubeThumbnailUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            binding.tvPosterTrailer.text = item.name
            itemView.setOnClickListener {
                itemClick(item) // Panggil onItemClick ketika item diklik
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemVideosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int {
        return trailers.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trailers[position])
    }

}