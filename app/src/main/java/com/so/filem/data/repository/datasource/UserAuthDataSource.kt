package com.so.filem.data.repository.datasource

import com.so.filem.data.firebase.User

interface UserAuthDataSource {
    suspend fun signInWithCredential(token: String):Pair<Boolean, User?>
    fun getCurrentUser(): User?
    fun logoutUser()
}