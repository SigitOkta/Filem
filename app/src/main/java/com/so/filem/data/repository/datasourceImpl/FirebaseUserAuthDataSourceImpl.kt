/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:06 AM
 */

package com.so.filem.data.repository.datasourceImpl

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.so.filem.data.firebase.User
import com.so.filem.data.repository.datasource.UserAuthDataSource
import kotlinx.coroutines.tasks.await

class FirebaseUserAuthDataSourceImpl(private val firebaseAuth: FirebaseAuth) : UserAuthDataSource {
    override suspend fun signInWithCredential(token: String): Pair<Boolean, User?> {
        val credential = GoogleAuthProvider.getCredential(token,null)
        val result = firebaseAuth.signInWithCredential(credential).await()
        return if (result != null ){
            Pair(true,firebaseAuth.currentUser?.toUserObject())
        } else {
            Pair(false, null)
        }
    }

    override fun getCurrentUser(): User? {
        return firebaseAuth.currentUser?.toUserObject()
    }

    override fun logoutUser() {
        firebaseAuth.signOut()
    }
}

fun FirebaseUser.toUserObject():User {
    return User(
        uid,
        displayName.orEmpty(),
        email.orEmpty(),
        photoUrl.toString()
    )
}