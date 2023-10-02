package com.so.filem.di

import android.app.Application
import androidx.room.Room
import com.so.filem.data.local.dao.movie.MovieDao
import com.so.filem.data.local.dao.movie.MoviePagingDao
import com.so.filem.data.local.dao.movie.MovieRemoteKeyDao
import com.so.filem.data.local.dao.tvShow.TvDao
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
        Room.databaseBuilder(app, TMDBDatabase::class.java, "db_tmdb")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideMoviePagingDao(db: TMDBDatabase): MoviePagingDao = db.moviePagingDao()

    @Provides
    fun provideMovieRemoteKeysDao(db: TMDBDatabase): MovieRemoteKeyDao = db.movieRemoteKeysDao()

    @Provides
    fun provideMovieDao(db: TMDBDatabase): MovieDao = db.movieDao()

    @Provides
    fun provideTvDao(db: TMDBDatabase): TvDao = db.tvDao()
}