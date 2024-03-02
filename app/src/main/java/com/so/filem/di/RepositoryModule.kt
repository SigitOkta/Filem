/*
 * Created by SigitO
 * Copyright (c) 2024 . All rights reserved.
 * Last modified 3/2/24, 11:03 AM
 */

package com.so.filem.di

import com.so.filem.data.repository.MovieRepositoryImpl
import com.so.filem.data.repository.TvRepositoryImpl
import com.so.filem.data.repository.datasource.MovieLocalDataSource
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.data.repository.datasource.TvLocalDataSource
import com.so.filem.data.repository.datasource.TvRemoteDataSource
import com.so.filem.domain.repository.MovieRepository
import com.so.filem.domain.repository.TvRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMoviesRepository(
        movieRemoteDataSource: MovieRemoteDataSource,
        movieLocalDataSource: MovieLocalDataSource,
    ): MovieRepository =
        MovieRepositoryImpl(
            movieRemoteDataSource,
            movieLocalDataSource,

            )

    @Provides
    fun provideTvShowsRepository(
        tvLocalDataSource: TvLocalDataSource,
        tvRemoteDataSource: TvRemoteDataSource
    ): TvRepository =
        TvRepositoryImpl(tvRemoteDataSource, tvLocalDataSource)
}