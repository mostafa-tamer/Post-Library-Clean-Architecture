package com.mostafatamer.postlibrary.data.local.repositoty

import com.mostafatamer.postlibrary.data.local.dao.FavoritePostToSyncDao
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostToSyncEntity
import com.mostafatamer.postlibrary.domain.model.PostEntityList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalFavoritePostToSyncRepository @Inject constructor(
    private val favoritePostToSyncDao: FavoritePostToSyncDao,
) {
    suspend fun insert(favoritePost: FavoritePostToSyncEntity) {
        favoritePostToSyncDao.insert(favoritePost)
    }

    suspend fun deleteAll() {
        favoritePostToSyncDao.deleteAll()
    }

    fun getAll(): Flow<PostEntityList> {
        return favoritePostToSyncDao.getAll()
    }
} 