package com.so.filem.data.repository.datasource

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.firebase.User

interface ThreadDataSource {
    suspend fun createThread(threadItem: ThreadItem): Boolean
    suspend fun createSubThread(parentThreadId: String, subThreadItem: SubThreadItem) : Boolean
    fun getThreadById(id: String, mediaType:Int): FirebaseRecyclerOptions<ThreadItem>
    fun getSubThread(parentThreadId: String) : FirebaseRecyclerOptions<SubThreadItem>
    suspend fun isCurrentUserInList(parentThreadId: String, currentUser: User?) : Boolean
}