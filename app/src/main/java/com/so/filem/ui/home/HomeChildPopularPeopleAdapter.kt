/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.so.filem.R
import com.so.filem.databinding.ItemHomePeoplePosterBinding
import com.so.filem.domain.model.Cast
import timber.log.Timber

class HomeChildPopularPeopleAdapter(
    private val itemClick: (Cast) -> Unit
) :
    RecyclerView.Adapter<HomeChildPopularPeopleAdapter.PeopleViewHolder>() {

    private var items: MutableList<Cast> = mutableListOf()

    fun setItems(items: List<Cast>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
        Timber.tag("AdapterPeopleChild").d(items.toString())
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PeopleViewHolder {
        val binding =
            ItemHomePeoplePosterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PeopleViewHolder(binding, itemClick)
    }

    override fun onBindViewHolder(holder: PeopleViewHolder, position: Int) {
        holder.bindView(items[position])
    }

    override fun getItemCount(): Int = items.size
    class PeopleViewHolder(
        private val binding: ItemHomePeoplePosterBinding,
        val itemClick: (Cast) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(item: Cast) {
            binding.ivPoster.load(item.profileImageUrl){
                crossfade(true)
                placeholder(R.drawable.ic_placeholder_poster)
            }
            binding.tvPosterName.text = item.actorName
            itemView.setOnClickListener { itemClick(item) }
        }
    }

}