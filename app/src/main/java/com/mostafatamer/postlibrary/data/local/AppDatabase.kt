package com.mostafatamer.postlibrary.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mostafatamer.postlibrary.data.local.dao.CommentDao
import com.mostafatamer.postlibrary.data.local.dao.FavoritePostToSyncDao
import com.mostafatamer.postlibrary.data.local.dao.MockServerFavoritePostDao
import com.mostafatamer.postlibrary.data.local.dao.PostDao
import com.mostafatamer.postlibrary.data.local.entity.CommentEntity
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostToSyncEntity
import com.mostafatamer.postlibrary.data.local.entity.MockServerFavoritePostEntity
import com.mostafatamer.postlibrary.data.local.entity.PostEntity

@Database(
    entities = [
        PostEntity::class,
        CommentEntity::class,
        MockServerFavoritePostEntity::class,
        FavoritePostToSyncEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun postDao(): PostDao
    abstract fun commentDao(): CommentDao
    abstract fun favoritePostDao(): MockServerFavoritePostDao
    abstract fun favoritePostToSyncDao(): FavoritePostToSyncDao
}