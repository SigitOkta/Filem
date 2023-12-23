package com.so.filem.data.repository

import com.so.filem.data.firebase.User
import com.so.filem.data.repository.datasource.UserAuthDataSource

interface UserRepository {
    suspend fun signInWithCredential(token: String): Pair<Boolean, User?>
    fun getCurrentUser(): User?
    fun logoutUser()
}

class UserRepositoryImpl(
    private val userAuthDataSource: UserAuthDataSource,
) : UserRepository {
    override suspend fun signInWithCredential(token: String): Pair<Boolean, User?> {
        return userAuthDataSource.signInWithCredential(token)
    }

    override fun getCurrentUser(): User? {
        return userAuthDataSource.getCurrentUser()
    }

    override fun logoutUser() {
        return userAuthDataSource.logoutUser()
    }


}