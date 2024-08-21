package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritePostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritePost: FavoritePostEntity)

    @Delete
    suspend fun removeFromFavorites(favoritePost: FavoritePostEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_posts WHERE postId = :postId)")
    fun isFavorite(postId: Int): Flow<Boolean>
}