package com.mostafatamer.postlibrary.data.remote.dto

import com.mostafatamer.postlibrary.domain.model.Comment

data class CommentDto(
    val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String,
) {
    fun toComment() = Comment(
        id = id,
        postId = postId,
        name = name,
        email = email,
        body = body
    )
}