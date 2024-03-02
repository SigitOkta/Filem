/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.domain.repository

import com.firebase.ui.database.FirebaseRecyclerOptions
import com.so.filem.data.firebase.SubThreadItem
import com.so.filem.data.firebase.ThreadItem
import com.so.filem.data.firebase.User

interface ThreadRepository {
    suspend fun createThread(threadItem: ThreadItem): Boolean
    suspend fun createSubThread(
        parentThreadId: String,
        subThreadItem: SubThreadItem
    ): Boolean

    suspend fun createMember(
        parentThreadId: String,
        user: User
    ): Boolean

    fun getThreadById(movieId: String, mediaType: Int): FirebaseRecyclerOptions<ThreadItem>
    fun getSubThread(parentThreadId: String): FirebaseRecyclerOptions<SubThreadItem>

    suspend fun isCurrentUserInMember(parentThreadId: String, currentUser: User?) : Boolean
    suspend fun isCurrentUserInCreator(parentThreadId: String, currentUser: User?) : Boolean
}