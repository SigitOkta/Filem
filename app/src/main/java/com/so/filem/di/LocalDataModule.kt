package com.so.filem.di

import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.repository.datasourceImpl.MovieLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {
    @Provides
    fun provideLocalDataSource(api : ApiService, db : TMDBDatabase): MovieLocalDataSource =
        MovieLocalDataSourceImpl(api, db)
}