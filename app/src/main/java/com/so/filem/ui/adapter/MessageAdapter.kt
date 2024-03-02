/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseError
import com.so.filem.R
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.User
import com.so.filem.databinding.ItemChatMessageBinding

class MessageAdapter(
    dataStream: FirebaseRecyclerOptions<SubThreadItem>,
    private val user: User?,
    private val onDataExist: () -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit,
    private val onDataEmpty: () -> Unit,
    private val onDataError: (e: Exception) -> Unit
) : FirebaseRecyclerAdapter<SubThreadItem, MessageAdapter.MessageViewHolder>(dataStream) {

    init {
        onLoading(true)
    }
    class MessageViewHolder(private val binding: ItemChatMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(chat: SubThreadItem, user: User?) {
            if (!user?.email.isNullOrEmpty() && chat.sender?.email == user?.email) {
                binding.groupOtherUserChat.isVisible = false
                binding.tvMessageUser.isVisible = true
                binding.tvMessageUser.text = chat.message
            } else {
                binding.groupOtherUserChat.isVisible = true
                binding.tvMessageUser.isVisible = false
                binding.tvMessageOtherUser.text = chat.message
                binding.ivChatPhoto.load(chat.sender?.photoProfileUrl) {
                    crossfade(true)
                    placeholder(R.drawable.ic_person)
                    error(R.drawable.ic_person)
                    transformations(CircleCropTransformation())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemChatMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int,
        model: SubThreadItem
    ) {
        holder.bind(model, user)
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