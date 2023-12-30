package com.so.filem.data.repository.datasourceImpl

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.repository.datasource.ThreadDataSource
import com.so.filem.domain.utils.setValueAppendId

class ThreadDataSourceImpl(private val firebaseDatabase: FirebaseDatabase) : ThreadDataSource {
    override suspend fun createThread(threadItem: ThreadItem): Boolean {
        return getParentChild().push()
            .setValueAppendId { id -> threadItem.apply { this.id = id } }
    }

    override suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Boolean {
        return getThreadReplyChild(parentThreadId)
            .push()
            .setValueAppendId { id ->
                subThreadItem.apply {
                    this.id = id
                }
            }
    }

    override fun getThreadById(
        id: String,
        mediaType: Int
    ): FirebaseRecyclerOptions<ThreadItem> {
        val query =
            getParentChild().orderByChild("idMediaType").equalTo(mediaType.toString()+"_"+ id)
        return FirebaseRecyclerOptions.Builder<ThreadItem>()
            .setQuery(query, ThreadItem::class.java)
            .build()
    }

    override fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem> {
        return FirebaseRecyclerOptions.Builder<SubThreadItem>()
            .setQuery(getThreadReplyChild(parentThreadId), SubThreadItem::class.java)
            .build()
    }

    private fun getParentChild() =
        firebaseDatabase.reference.child(THREADS_CHILD)

    private fun getThreadReplyChild(parentThreadId: String) =
        getParentChild().child(parentThreadId).child(THREADS_REPLY_CHILD)


    companion object {
        private const val THREADS_CHILD = "threads"
        private const val THREADS_REPLY_CHILD = "threads_reply"
    }
}