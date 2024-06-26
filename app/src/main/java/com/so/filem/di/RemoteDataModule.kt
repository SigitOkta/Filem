/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.di

import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.data.repository.datasource.TvRemoteDataSource
import com.so.filem.data.repository.datasourceImpl.MovieRemoteDataSourceImpl
import com.so.filem.data.repository.datasourceImpl.TvRemoteDataSourceImpl
import com.so.filem.domain.model.MovieFilter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteDataModule {
    @Provides
    fun provideMoviesRemoteDataSource(api: ApiService) : MovieRemoteDataSource =
        MovieRemoteDataSourceImpl(api)

    @Provides
    fun provideTvsRemoteDataSource(api: ApiService) : TvRemoteDataSource =
        TvRemoteDataSourceImpl(api)
}