package com.so.filem.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.so.filem.data.local.dao.movie.MovieDao
import com.so.filem.data.local.dao.movie.MovieRemoteKeyDao
import com.so.filem.domain.model.movie.Movie
import com.so.filem.domain.model.movie.MovieRemoteKey

@Database(
    entities = [Movie::class, MovieRemoteKey::class],
    version = 1,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao
}