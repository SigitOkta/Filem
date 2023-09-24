package com.so.filem.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemHomePosterBinding
import com.so.filem.databinding.ItemMediaParentBinding
import com.so.filem.databinding.ItemPosterBinding
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.model.MediaItem
import com.so.filem.domain.model.Movie
import com.so.filem.domain.utils.Constants

class HomeChildAdapter(private val childList: List<Movie>) :
    RecyclerView.Adapter<HomeChildAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(private val binding: ItemHomePosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(childItem: Movie) {
            binding.ivPoster.load(childItem.posterUrl){
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            if (childItem.title.isNullOrEmpty()){
                binding.tvPoster.text = "-"
            } else {
                binding.tvPoster.text = childItem.title
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val binding =
            ItemHomePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChildViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        holder.bind(childList[position])
    }

    override fun getItemCount(): Int {
        return childList.size
    }
}