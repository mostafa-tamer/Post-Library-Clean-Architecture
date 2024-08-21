package com.mostafatamer.postlibrary.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.mostafatamer.postlibrary.data.local.entity.CommentEntity

@Dao
interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comment: CommentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comments: List<CommentEntity>)

    @Upsert
    suspend fun upsert(comments: List<CommentEntity>)

    @Query("SELECT * FROM comments WHERE postId = :postId")
    suspend fun getCommentsByPostId(postId: Int): List<CommentEntity>

    @Query("SELECT * FROM comments WHERE id = :id")
    suspend fun getCommentById(id: Int): CommentEntity?

    @Query("DELETE FROM comments WHERE id = :id")
    suspend fun deleteCommentById(id: Int)
}