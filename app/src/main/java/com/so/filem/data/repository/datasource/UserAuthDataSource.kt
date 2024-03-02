/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository.datasource

import com.so.filem.data.firebase.User

interface UserAuthDataSource {
    suspend fun signInWithCredential(token: String):Pair<Boolean, User?>
    fun getCurrentUser(): User?
    fun logoutUser()
}