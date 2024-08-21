package com.mostafatamer.postlibrary.data.remote.dto

import com.mostafatamer.postlibrary.domain.model.Post

data class PostDto(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
){
    fun toPost() = Post(
        id = id,
        userId = userId,
        title = title,
        body = body
    )
}