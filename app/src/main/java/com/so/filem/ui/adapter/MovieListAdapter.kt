package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.domain.utils.Constants
import com.so.filem.ui.detail.movie.DetailMovieActivity
import timber.log.Timber

class MovieListAdapter(
) : PagingDataAdapter<MoviePaging, MovieListAdapter.MyViewHolder>(DIFF_CALLBACK) {

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

    class MyViewHolder(
        private val binding: ItemPosterMovieGridBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: MoviePaging) {
            val posterUrl =
                if (data.poster_path != null) Constants.POSTER_URL + data.poster_path else null
            //val backdropUrl = if ( data.backdrop_path != null) Constants.BACKDROP_URL + data.backdrop_path else null
            binding.apply{
                ivPoster.load(posterUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_placeholder_poster)
                }
                tvPoster.text = data.title
            }
            itemView.setOnClickListener {
                DetailMovieActivity.startActivity(itemView.context,data.id)
                Timber.tag("adapter").d(data.title)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MoviePaging>() {
            override fun areItemsTheSame(oldItem: MoviePaging, newItem: MoviePaging): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MoviePaging, newItem: MoviePaging): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}