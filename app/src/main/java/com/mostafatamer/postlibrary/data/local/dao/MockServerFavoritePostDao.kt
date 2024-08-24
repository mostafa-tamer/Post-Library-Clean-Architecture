package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafatamer.postlibrary.data.local.entity.MockServerFavoritePostEntity
import kotlinx.coroutines.flow.Flow


/**
 * used by the mock server to store favorite posts in the mock server database
 */
@Dao
interface MockServerFavoritePostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritePost: MockServerFavoritePostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritePost: List<MockServerFavoritePostEntity>)

    @Query("SELECT  * FROM mock_server_favorite_posts")
    suspend fun getAllFavoritePosts(): List<MockServerFavoritePostEntity>

    @Delete
    suspend fun removeFromFavorites(favoritePost: MockServerFavoritePostEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM mock_server_favorite_posts WHERE id = :postId)")
    fun isFavorite(postId: Int): Flow<Boolean>
}