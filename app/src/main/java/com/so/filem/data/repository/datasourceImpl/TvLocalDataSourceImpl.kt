package com.so.filem.data.repository.datasourceImpl

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.so.filem.data.local.dao.tvShow.entity.TvPaging
import com.so.filem.data.local.dao.tvShow.entity.TvsEntity
import com.so.filem.data.local.db.TMDBDatabase
import com.so.filem.data.paging.TvRemoteMediator
import com.so.filem.data.remote.network.ApiService
import com.so.filem.data.repository.datasource.TvLocalDataSource
import com.so.filem.domain.model.TvFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TvLocalDataSourceImpl @Inject constructor(
    private val api: ApiService,
    private val db: TMDBDatabase,
) : TvLocalDataSource {
    private val tvDao = db.tvDao()
    private val tvPagingDao = db.tvPagingDao()
    @OptIn(ExperimentalPagingApi::class)
    override fun getTvShowsForPaging(filter: TvFilter): Flow<PagingData<TvPaging>> {
        val pagingSourceFactory = { tvPagingDao.getAllTvShows() }
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = TvRemoteMediator(
                api,
                db,
                filter
            ),
            pagingSourceFactory = pagingSourceFactory,
        ).flow
    }


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