package com.so.filem.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.so.filem.data.local.dao.movie.MovieDao
import com.so.filem.data.local.dao.movie.MoviePagingDao
import com.so.filem.data.local.dao.movie.MovieRemoteKeyDao
import com.so.filem.data.local.dao.movie.entity.MoviePaging
import com.so.filem.data.local.dao.movie.entity.MovieRemoteKey
import com.so.filem.data.local.dao.movie.entity.MoviesEntity
import com.so.filem.data.local.dao.tvShow.TvDao
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity

@Database(
    entities = [MoviePaging::class, MovieRemoteKey::class, MoviesEntity::class, TvsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class TMDBDatabase : RoomDatabase() {
    abstract fun moviePagingDao(): MoviePagingDao
    abstract fun movieRemoteKeysDao(): MovieRemoteKeyDao

    abstract fun movieDao() : MovieDao

    abstract fun tvDao(): TvDao

}