package com.so.filem.ui.detail.cast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.databinding.ItemMediaParentBinding
import com.so.filem.ui.adapter.MovieListAdapter

class MediaParentAdapter(private val mediaItemList: List<MediaContent>) :
    RecyclerView.Adapter<MediaParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(private val binding: ItemMediaParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parentItem: MediaContent) {
            binding.ivLogo.setImageResource(parentItem.image)
            binding.tvTitleMedia.text = parentItem.title
            binding.rvMediaChild.setHasFixedSize(true)
            binding.rvMediaChild.layoutManager = LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)

            val adapter = MediaChildAdapter(parentItem.childItemList)
            binding.rvMediaChild.adapter = adapter
            binding.rvMediaChild.isNestedScrollingEnabled = false;
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding =
            ItemMediaParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mediaItemList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(mediaItemList[position])
    }

}