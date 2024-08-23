package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostToSyncEntity
import com.mostafatamer.postlibrary.domain.model.PostEntityList
import kotlinx.coroutines.flow.Flow

/**
 * act as a buffer to sync the favorite posts to the server
 */
@Dao
interface FavoritePostToSyncDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoritePost: FavoritePostToSyncEntity)

    @Query("DELETE FROM favorite_posts_to_sync")
    suspend fun deleteAll()

    @Query(
        """
        SELECT posts.* FROM posts 
        INNER JOIN favorite_posts_to_sync ON posts.id = favorite_posts_to_sync.postId
    """
    )
    fun getAll(): Flow<PostEntityList>
}


