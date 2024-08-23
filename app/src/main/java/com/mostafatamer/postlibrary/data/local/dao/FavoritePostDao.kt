package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostEntity
import com.mostafatamer.postlibrary.data.local.entity.PostEntity
import kotlinx.coroutines.flow.Flow


/**
 * used by the mock server repository to act as a server database
 */
@Dao
interface FavoritePostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritePost: FavoritePostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoritePost: List<FavoritePostEntity>)

    @Query(
        """
        SELECT posts.* FROM posts 
        INNER JOIN favorite_posts ON posts.id = favorite_posts.postId
    """
    )
    suspend fun getAllFavoritePosts(): List<PostEntity>

    @Delete
    suspend fun removeFromFavorites(favoritePost: FavoritePostEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_posts WHERE postId = :postId)")
    fun isFavorite(postId: Int): Flow<Boolean>
}