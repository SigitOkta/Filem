package com.so.filem.data.repository.datasourceImpl

import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.repository.datasource.TvLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvLocalDataSourceImpl @Inject constructor(
    private val db: TMDBDatabase,
) : TvLocalDataSource {
    private val tvDao = db.tvDao()
    override suspend fun saveTvFavorite(tv: TvsEntity): Long {
        return tvDao.insertTv(tv)
    }

    override fun getFavoriteTv(tvId: Long): Flow<TvsEntity?> {
       return tvDao.getTv(tvId)
    }

    override fun getAllFavoriteTvs(): Flow<List<TvsEntity>> {
        return tvDao.getAllFavoriteTvShows()
    }

    override suspend fun updateTvFavorite(tvId: String, isFavorite: Boolean) {
        return tvDao.updateFavorite(tvId , isFavorite)
    }

    override suspend fun deleteFavoriteTv(tvId: Long): Int {
       return tvDao.deleteTvById(tvId)
    }

}