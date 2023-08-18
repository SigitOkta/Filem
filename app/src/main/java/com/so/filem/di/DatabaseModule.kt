package com.so.filem.di

import android.app.Application
import androidx.room.Room
import com.so.filem.data.local.dao.movie.MoviePagingDao
import com.so.filem.data.local.dao.movie.MovieRemoteKeyDao
import com.so.filem.data.local.db.TMDBDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TMDBDatabase =
        Room.databaseBuilder(app, TMDBDatabase::class.java, "db_tmdb").fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMovieDao(db: TMDBDatabase) : MoviePagingDao = db.moviePagingDao()

    @Provides
    fun provideMovieRemoteKeysDao(db: TMDBDatabase) : MovieRemoteKeyDao = db.movieRemoteKeysDao()
}