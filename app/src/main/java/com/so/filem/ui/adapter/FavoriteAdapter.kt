package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemPosterMovieGridBinding
import com.so.filem.domain.model.Movie
import com.so.filem.ui.detail.movie.DetailMovieActivity

class FavoriteAdapter(private val movies: List<Movie>) :
    RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemPosterMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Movie) {
            binding.ivPoster.load(item.posterUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            itemView.setOnClickListener {
                DetailMovieActivity.startActivity(itemView.context,item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemPosterMovieGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
      return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}