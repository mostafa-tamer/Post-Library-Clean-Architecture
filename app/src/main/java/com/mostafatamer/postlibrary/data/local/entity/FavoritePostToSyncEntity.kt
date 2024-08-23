package com.mostafatamer.postlibrary.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * this acts like a buffer to sync the favorite posts to the server
 */
@Entity(
    tableName = "favorite_posts_to_sync",
    foreignKeys = [
        ForeignKey(
            entity = PostEntity::class,
            parentColumns = ["id"],
            childColumns = ["postId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index("postId")]
)
data class FavoritePostToSyncEntity(
    @PrimaryKey val postId: Int,
)