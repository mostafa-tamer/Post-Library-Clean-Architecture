package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mostafatamer.postlibrary.data.local.entity.PostEntity

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: List<PostEntity>)

    @Upsert
    suspend fun upsert(post: List<PostEntity>)

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostEntity?

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<PostEntity>

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePostById(id: Int)

    @Delete
    suspend  fun deletePosts(oldPosts: MutableList<PostEntity>)
}