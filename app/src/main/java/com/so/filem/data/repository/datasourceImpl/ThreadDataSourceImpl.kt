package com.so.filem.data.repository.datasourceImpl

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.firebase.User
import com.so.filem.data.repository.datasource.ThreadDataSource
import com.so.filem.domain.utils.setValueAppendId
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class ThreadDataSourceImpl(private val firebaseDatabase: FirebaseDatabase) : ThreadDataSource {
    override suspend fun createThread(threadItem: ThreadItem): Boolean {
        return getParentChild().push()
            .setValueAppendId { id ->
                threadItem.apply {
                    this.id = id
                }
            }
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

    override suspend fun createMember(parentThreadId: String, user: User): Boolean {
        return getThreadMember(parentThreadId)
            .child(user.id).setValueAppendId { id ->
                user.apply {
                    this.id = id
                }
            }
    }

    override fun getThreadById(
        id: String,
        mediaType: Int
    ): FirebaseRecyclerOptions<ThreadItem> {
        val query =
            getParentChild().orderByChild("idMediaType").equalTo(mediaType.toString() + "_" + id)
        return FirebaseRecyclerOptions.Builder<ThreadItem>()
            .setQuery(query, ThreadItem::class.java)
            .build()
    }


    override fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem> {
        return FirebaseRecyclerOptions.Builder<SubThreadItem>()
            .setQuery(getThreadReplyChild(parentThreadId), SubThreadItem::class.java)
            .build()
    }

    override suspend fun isCurrentUserInMember(
        parentThreadId: String,
        currentUser: User?
    ): Boolean {
        val threadSnapshot = getThreadMember(parentThreadId).child(currentUser?.id ?: "").get().await()
        val thread = threadSnapshot.getValue(User::class.java)
        return thread?.id == currentUser?.id
    }

    override suspend fun isCurrentUserInCreator(
        parentThreadId: String,
        currentUser: User?
    ): Boolean {
        val threadSnapshot = getParentChild().child(parentThreadId).get().await()
        val thread = threadSnapshot.getValue(ThreadItem::class.java)
        Timber.tag("ThreadDetails").d(thread?.creator?.id)
        Timber.tag("ThreadDetailsC").d(currentUser?.id)
        return thread?.creator?.id == currentUser?.id
    }

    override suspend fun isMember(currentUser: User?): Boolean {
        val query = getParentChild().orderByChild("threads_members/$currentUser").get().await()
        val thread = query.getValue(User::class.java)
        return thread?.id == currentUser?.id
    }


    private fun getParentChild() =
        firebaseDatabase.reference.child(THREADS_CHILD)

    private fun getThreadReplyChild(parentThreadId: String) =
        getParentChild().child(parentThreadId).child(THREADS_REPLY_CHILD)

    private fun getThreadMember(parentThreadId: String) =
        getParentChild().child(parentThreadId).child(THREADS_MEMBER)

    companion object {
        private const val THREADS_CHILD = "threads"
        private const val THREADS_REPLY_CHILD = "threads_reply"
        private const val THREADS_MEMBER = "threads_members"
    }
}