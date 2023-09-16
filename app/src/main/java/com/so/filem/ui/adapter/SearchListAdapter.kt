package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterMovieHorizontalBinding
import com.so.filem.domain.model.Movie
import com.so.filem.ui.movie.detail.DetailMovieActivity
import timber.log.Timber

class SearchListAdapter(
) : PagingDataAdapter<Movie, SearchListAdapter.MyViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPosterMovieHorizontalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    class MyViewHolder(
        private val binding: ItemPosterMovieHorizontalBinding,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Movie) {
            val posterUrl = data.posterUrl
            //val backdropUrl = if ( data.backdrop_path != null) Constants.BACKDROP_URL + data.backdrop_path else null
            binding.imageViewPoster.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            //title
            binding.textViewTitle.text = data.title
            // date
            val dateStr = data.release_date
            val parts = dateStr?.split("-")
            val year = parts?.get(0)
            binding.textViewYear.text = year
            //rating
            val ratingValue = data.vote_average
            val maxRating = 10.0
            val scaledRating = (ratingValue?.div(maxRating))?.times(binding.ratingBar.numStars)
            if (scaledRating != null) {
                binding.ratingBar.rating = scaledRating.toFloat()
            }

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