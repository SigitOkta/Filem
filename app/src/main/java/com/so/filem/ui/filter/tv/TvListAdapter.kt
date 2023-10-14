package com.so.filem.ui.filter.tv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.utils.Constants
import com.so.filem.ui.detail.tv.DetailTvShowActivity

class TvListAdapter() :
    PagingDataAdapter<TvPaging, TvListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(
        private val binding: ItemPosterMovieGridBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: TvPaging) {
            val posterUrl =
                if (data.posterPath != null) Constants.POSTER_URL + data.posterPath else null
            //val backdropUrl = if ( data.backdrop_path != null) Constants.BACKDROP_URL + data.backdrop_path else null
            binding.apply {
                ivPoster.load(posterUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_poster)
                }
                tvPoster.text = data.name
            }
            itemView.setOnClickListener {
                DetailTvShowActivity.startActivity(itemView.context, data.id)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TvPaging>() {
            override fun areItemsTheSame(oldItem: TvPaging, newItem: TvPaging): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TvPaging, newItem: TvPaging): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPosterMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }
}