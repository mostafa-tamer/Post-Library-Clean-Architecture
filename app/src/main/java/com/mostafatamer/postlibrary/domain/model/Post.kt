package com.mostafatamer.postlibrary.domain.model

import com.mostafatamer.postlibrary.data.local.entity.PostEntity

data class Post(
    val id: Int,
    val userId: Int,
    val title: String,
    val body: String,
) {
    fun toPostEntity(): PostEntity {
        return PostEntity(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}