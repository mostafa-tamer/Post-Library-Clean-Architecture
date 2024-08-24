package com.mostafatamer.postlibrary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mock_server_favorite_posts")
data class MockServerFavoritePostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
)