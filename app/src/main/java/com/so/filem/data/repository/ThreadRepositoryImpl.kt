package com.so.filem.data.repository

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.repository.datasource.ThreadDataSource
import com.so.filem.domain.repository.ThreadRepository

class ThreadRepositoryImpl(private val dataSource: ThreadDataSource) :
    ThreadRepository {
    override suspend fun createThread(threadItem: ThreadItem): Boolean {
        return dataSource.createThread(threadItem)
    }

    override suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Boolean {
        return dataSource.createSubThread(parentThreadId, subThreadItem)
    }

    override fun getThreadById(movieId: String, mediaType: Int): FirebaseRecyclerOptions<ThreadItem> {
        return dataSource.getThreadById(movieId, mediaType)
    }

    override fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem> {
        return dataSource.getSubThread(parentThreadId)
    }
}