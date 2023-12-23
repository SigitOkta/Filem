package com.so.filem.di

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.so.filem.BuildConfig
import com.so.filem.data.repository.UserRepository
import com.so.filem.data.repository.UserRepositoryImpl
import com.so.filem.data.repository.datasource.UserAuthDataSource
import com.so.filem.data.repository.datasourceImpl.FirebaseUserAuthDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    fun provideGoogleSignInOptions(
        app: Application
    ) = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.FIREBASE_WEB_CLIENT_ID)
        .requestEmail()
        .build()

    @Provides
    fun provideGoogleSignInClient(
        app: Application,
        options: GoogleSignInOptions
    ) = GoogleSignIn.getClient(app, options)

    @Provides
    fun provideUserAuthDataSource(
        auth : FirebaseAuth
    ): UserAuthDataSource =
        FirebaseUserAuthDataSourceImpl(auth)
    @Provides
    fun provideUserRepository(
        userAuthDataSource: UserAuthDataSource
    ): UserRepository =
        UserRepositoryImpl(userAuthDataSource)
}