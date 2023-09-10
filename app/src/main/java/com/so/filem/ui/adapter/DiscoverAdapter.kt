package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.databinding.ItemPosterViewpagerBinding
import com.so.filem.domain.model.Cast
import com.so.filem.domain.model.Movie

class DiscoverAdapter(private val discover: ArrayList<Movie>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<DiscoverAdapter.ImageViewHolder>() {

    class ImageViewHolder(private val binding: ItemPosterViewpagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie){
            binding.ivPosterViewPager.load(item.posterUrl) {
                //crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding =
            ItemPosterViewpagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(discover[position])

        if (position == discover.size - 1) {
            viewPager2.post(runnable)
        }
    }

    override fun getItemCount(): Int {
        return discover.size
    }

    private val runnable = Runnable {
        discover.addAll(discover)
        notifyDataSetChanged()
    }
}