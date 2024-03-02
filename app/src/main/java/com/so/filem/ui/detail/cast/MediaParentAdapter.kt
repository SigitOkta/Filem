/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.detail.cast

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.so.filem.R
import com.so.filem.databinding.ItemCastMediaParentBinding

class MediaParentAdapter(private val mediaItemList: List<MediaContent>) :
    RecyclerView.Adapter<MediaParentAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(private val binding: ItemCastMediaParentBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(parentItem: MediaContent) {

            binding.ivLogo.setImageResource(parentItem.image)
            binding.tvTitleMedia.text = parentItem.title


            binding.rvMediaChild.layoutManager =
                LinearLayoutManager(binding.root.context, LinearLayoutManager.HORIZONTAL, false)
            val adapter = MediaChildAdapter(parentItem.childItemList)
            binding.rvMediaChild.adapter = adapter
            binding.rvMediaChild.setHasFixedSize(true)
            //binding.rvMediaChild.isNestedScrollingEnabled = true
            //expandable functionality
            val isExpandable = parentItem.isOpen
            binding.rvMediaChild.visibility = if (isExpandable) View.VISIBLE else View.GONE

            binding.clMedia.setOnClickListener {
                val upAnim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_up)
                val downAnim = AnimationUtils.loadAnimation(binding.root.context, R.anim.anim_down)
                val parentContent = mediaItemList[bindingAdapterPosition]

                isAnyItemExpanded(bindingAdapterPosition)
                parentContent.isOpen = !parentContent.isOpen


                if (parentContent.isOpen) {
                    binding.rvMediaChild.startAnimation(downAnim)

                } else {
                    binding.rvMediaChild.startAnimation(upAnim)
                }
                notifyItemChanged(bindingAdapterPosition)
            }

            val listener = object : RecyclerView.OnItemTouchListener {
                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    val action = e.action
                    return if (binding.rvMediaChild.canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                        when (action) {
                            MotionEvent.ACTION_MOVE -> rv.parent
                                .requestDisallowInterceptTouchEvent(true)
                        }
                        false
                    } else {
                        when (action) {
                            MotionEvent.ACTION_MOVE -> rv.parent
                                .requestDisallowInterceptTouchEvent(false)
                        }
                        binding.rvMediaChild.removeOnItemTouchListener(this)
                        true
                    }
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            }

            binding.rvMediaChild.addOnItemTouchListener(listener)
        }

        private fun isAnyItemExpanded(bindingAdapterPosition: Int) {
            val temp = mediaItemList.indexOfFirst {
                it.isOpen
            }

            if (temp >= 0 && temp != bindingAdapterPosition) {
                mediaItemList[temp].isOpen = false
                notifyItemChanged(temp)
            }
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val binding =
            ItemCastMediaParentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ParentViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return mediaItemList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(mediaItemList[position])
    }

}