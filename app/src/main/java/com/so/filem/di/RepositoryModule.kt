package com.so.filem.di

import com.so.filem.data.repository.MovieRepositoryImpl
import com.so.filem.data.repository.datasource.MovieRemoteDataSource
import com.so.filem.domain.repository.MovieRepository
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
    ): MovieRepository =
        MovieRepositoryImpl(movieRemoteDataSource)
}