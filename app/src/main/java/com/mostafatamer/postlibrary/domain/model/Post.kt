package com.mostafatamer.postlibrary.domain.model

import com.mostafatamer.postlibrary.data.local.entity.MockServerFavoritePostEntity
import com.mostafatamer.postlibrary.data.local.entity.PostEntity
import com.mostafatamer.postlibrary.data.remote.dto.PostDto

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

    fun toPostDto(): PostDto {
        return PostDto(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }

    fun toMockServerPostEntity(): MockServerFavoritePostEntity {
        return MockServerFavoritePostEntity(
            id = id,
            userId = userId,
            title = title,
            body = body
        )
    }
}