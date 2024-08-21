package com.mostafatamer.postlibrary.domain.model

import com.mostafatamer.postlibrary.data.local.entity.CommentEntity

data class Comment(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
) {
    fun toCommentEntity() = CommentEntity(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}