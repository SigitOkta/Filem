package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.databinding.ItemMovieHorizontalBinding
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.model.Movie
import com.so.filem.domain.utils.Constants
import com.so.filem.ui.movie.detail.DetailMovieActivity
import timber.log.Timber

class SearchListAdapter(
) : PagingDataAdapter<Movie, SearchListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class MyViewHolder(
        private val binding: ItemMovieHorizontalBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            val posterUrl = data.posterUrl
            //val backdropUrl = if ( data.backdrop_path != null) Constants.BACKDROP_URL + data.backdrop_path else null
            binding.imageViewPoster.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            binding.textViewTitle.text = data.title
            binding.textViewYear.text = data.release_date
            itemView.setOnClickListener {
                DetailMovieActivity.startActivity(itemView.context,data.id)
                Timber.tag("adapter").d(data.title)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}