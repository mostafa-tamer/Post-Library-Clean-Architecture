package com.mostafatamer.postlibrary

import com.mostafatamer.postlibrary.data.remote.dto.CommentDto
import com.mostafatamer.postlibrary.data.remote.dto.PostDto


val PostsResponse = listOf(
    PostDto(
        id = 1,
        userId = 1,
        title = "Test Post",
        body = "Test Body"
    ),
    PostDto(
        id = 2,
        userId = 1,
        title = "Test Post 2",
        body = "Test Body 2"
    ),
    PostDto(
        id = 3,
        userId = 1,
        title = "Test Post 2",
        body = "Test Body 3"
    ),
    PostDto(
        id = 4,
        userId = 2,
        title = "Test Post 2",
        body = "Test Body 4"
    ),
    PostDto(
        id = 5,
        userId = 2,
        title = "Test Post 2",
        body = "Test Body 5"
    )
)

val Posts = PostsResponse.map { it.toPost() }

val PostEntities = Posts.map { it.toPostEntity() }

val CommentsResponse = listOf(
    CommentDto(
        id = 1,
        postId = 1,
        name = "John Doe",
        email = "john.doe@example.com",
        body = "This is a comment body"
    ),
    CommentDto(
        id = 2,
        postId = 1,
        name = "Jane Smith",
        email = "jane.smith@example.com",
        body = "Another comment body"
    ),
    CommentDto(
        id = 3,
        postId = 2,
        name = "Alice Johnson",
        email = "alice.johnson@example.com",
        body = "Yet another comment body"
    ),
    CommentDto(
        id = 4,
        postId = 2,
        name = "Bob Brown",
        email = "bob.brown@example.com",
        body = "Comment body for post 2"
    ),
    CommentDto(
        id = 5,
        postId = 3,
        name = "Carol White",
        email = "carol.white@example.com",
        body = "Comment for post 3"
    )
)

val Comments = CommentsResponse.map { it.toComment() }

val CommentEntities = Comments.map { it.toCommentEntity() }


