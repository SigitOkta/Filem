/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.so.filem.R
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.databinding.ItemThreadBinding
import com.so.filem.ui.thread.threaddetail.ThreadDetailActivity

class ThreadListAdapter(
    dataStream: FirebaseRecyclerOptions<ThreadItem>,
    private val onDataExist: () -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit,
    private val onDataEmpty: () -> Unit,
    private val onDataError: (e: Exception) -> Unit
) : FirebaseRecyclerAdapter<ThreadItem, ThreadListAdapter.ThreadItemViewHolder>(dataStream) {

    init {
        onLoading(true)
    }

    class ThreadItemViewHolder(private val binding: ItemThreadBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ThreadItem) {
           /* val memberList = item.members
            for (i in 0 until memberList.size) {
                if (!user?.email.isNullOrEmpty() && memberList[i]?.email == user?.email) {
                    binding.llJoinButton.visibility = View.GONE
                } else {
                    binding.llJoinButton.visibility = View.VISIBLE
                }
            }
*/
            binding.ivProfilePict.load(item.creator?.photoProfileUrl) {
                crossfade(true)
                placeholder(R.drawable.ic_person)
                error(R.drawable.ic_person)
                transformations(CircleCropTransformation())
            }
            binding.tvTitleThread.text = item.title
            binding.tvNameThreadStarter.text = itemView.context.getString(
                R.string.text_container_display_creator_thread,
                item.creator?.displayName
            )
            itemView.setOnClickListener {
                ThreadDetailActivity.startActivity(itemView.context, item)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThreadItemViewHolder {
        return ThreadItemViewHolder(
            ItemThreadBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ThreadItemViewHolder, position: Int, model: ThreadItem) {
        holder.bind(model)
    }

    override fun onDataChanged() {
        super.onDataChanged()
        onLoading(false)
        if (itemCount < 1) {
            onDataEmpty()
        } else {
            onDataExist()
        }
    }

    override fun onError(error: DatabaseError) {
        super.onError(error)
        onDataError(error.toException())
    }

}