package com.mostafatamer.postlibrary.data.local.repositoty

import com.mostafatamer.postlibrary.data.local.dao.CommentDao
import com.mostafatamer.postlibrary.data.local.dao.PostDao
import com.mostafatamer.postlibrary.domain.model.Comment
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import javax.inject.Inject

class LocalPostRepository @Inject constructor(
    private val postDao: PostDao,
    private val commentDao: CommentDao,
) {
    suspend fun getPosts(): DataState<PostList> {
        val postsEntity = postDao.getAllPosts()
        val posts = postsEntity.map { it.toPost() }
        return if (posts.isNotEmpty()) DataState.Success(posts) else DataState.Empty
    }

    suspend fun getComments(postId: Int): DataState<CommentsList> {
        val commentsEntity = commentDao.getCommentsByPostId(postId)
        val comments = commentsEntity.map { it.toComment() }
        return if (comments.isNotEmpty()) DataState.Success(comments) else DataState.Empty
    }

    suspend fun savePosts(postList: PostList) {
        val posts = postList.map { it.toPostEntity() }
        postDao.upsert(posts)
    }

    suspend fun saveComments(comment: List<Comment>) {
        val commentEntities = comment.map { it.toCommentEntity() }
        commentDao.upsert(commentEntities)
    }

    suspend fun getPostById(postId: Int): DataState<Post> {
        val postEntity = postDao.getPostById(postId)
        return if (postEntity != null) DataState.Success(postEntity.toPost()) else DataState.Empty
    }
}
