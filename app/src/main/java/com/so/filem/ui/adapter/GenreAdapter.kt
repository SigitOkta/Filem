package com.so.filem.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.databinding.ItemGenreBinding
import com.so.filem.domain.model.Genre

class GenreAdapter(private val genres: List<Genre> ) :
    RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item:  Genre) {
            binding.tvDetailGenre.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }
}