package com.so.filem.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterBinding
import com.so.filem.domain.model.Search
import com.so.filem.ui.detail.cast.DetailCastActivity
import com.so.filem.ui.detail.movie.DetailMovieActivity
import com.so.filem.ui.detail.tv.DetailTvShowActivity
import timber.log.Timber

class SearchListAdapter(
) : PagingDataAdapter<Search, SearchListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class MyViewHolder(
        private val binding: ItemPosterBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Search) {
            Timber.tag("searchListAdapter").d(data.toString())
            when (data.mediaType) {
                "movie" -> {
                    binding.ivPoster.load(data.posterUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_poster)
                    }
                    binding.tvPoster.text = data.title
                    itemView.setOnClickListener {
                        DetailMovieActivity.startActivity(itemView.context,data.id)
                    }
                }
                "tv" -> {
                    binding.ivPoster.load(data.posterUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_poster)
                    }
                    binding.tvPoster.text = data.name
                    itemView.setOnClickListener {
                        DetailTvShowActivity.startActivity(itemView.context,data.id)
                    }
                }

                "person" -> {
                    binding.ivPoster.load(data.profileUrl) {
                        crossfade(true)
                        placeholder(R.drawable.ic_placeholder_poster)
                    }
                    binding.tvPoster.text = data.name
                    itemView.setOnClickListener{
                        DetailCastActivity.startActivity(itemView.context, data.id)
                    }
                }
            }

        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Search>() {
            override fun areItemsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Search, newItem: Search): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}