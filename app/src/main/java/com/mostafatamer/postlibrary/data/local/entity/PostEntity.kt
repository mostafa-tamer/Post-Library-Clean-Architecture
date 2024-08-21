package com.mostafatamer.postlibrary.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mostafatamer.postlibrary.domain.model.Post

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
){
    fun toPost(): Post {
        return Post(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}