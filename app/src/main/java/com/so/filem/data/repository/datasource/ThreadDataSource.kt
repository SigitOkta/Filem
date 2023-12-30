package com.so.filem.data.repository.datasource

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem

interface ThreadDataSource {
    suspend fun createThread(threadItem: ThreadItem): Boolean
    suspend fun createSubThread(parentThreadId: String, subThreadItem: SubThreadItem) : Boolean
    fun getThreadById(id: String, mediaType:Int): FirebaseRecyclerOptions<ThreadItem>
    fun getSubThread(parentThreadId: String) : FirebaseRecyclerOptions<SubThreadItem>
}