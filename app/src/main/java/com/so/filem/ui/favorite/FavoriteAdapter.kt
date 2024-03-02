/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.databinding.ItemPosterMovieGridBinding

class FavoriteAdapter<T : Any>(
    private val bindingInterface: FavoriteRecyclerBindingInterface<T>
) :
    RecyclerView.Adapter<FavoriteAdapter<T>.ViewHolder>() {

    private val dataSet = mutableListOf<T>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPosterMovieGridBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataSet[position]
        holder.bind(item, bindingInterface)
    }

    override fun getItemCount(): Int = dataSet.size
    fun updateDataSet(newDataSet: List<T>) {
        dataSet.clear()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }
    inner class ViewHolder(private val binding: ItemPosterMovieGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T, bindingInterface: FavoriteRecyclerBindingInterface<T>) =
            bindingInterface.bindData(item, binding)
    }
}

interface FavoriteRecyclerBindingInterface<T : Any> {
    fun bindData(item: T, binding: ItemPosterMovieGridBinding)
}

