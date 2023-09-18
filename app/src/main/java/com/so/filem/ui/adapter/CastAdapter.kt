package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemCastPosterBinding
import com.so.filem.domain.model.Cast
import com.so.filem.ui.detail.cast.DetailCastActivity
import com.so.filem.ui.detail.movie.DetailMovieActivity
import timber.log.Timber

class CastAdapter(private val casts: List<Cast>) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemCastPosterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cast){
            Timber.tag("activity-castAdapter").d(item.profileImageUrl)
            binding.ivCastPoster.load(item.profileImageUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            itemView.setOnClickListener {
                DetailCastActivity.startActivity(itemView.context,item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
           ItemCastPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return casts.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(casts[position])
    }

}