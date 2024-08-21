package com.mostafatamer.postlibrary.data.remote.repository

import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState

class RemotePostRepository(private val postApiService: PostApiService) {
    suspend fun getPosts(): DataState<PostList> = try {
        val result = postApiService.getPosts()

        if (result.isSuccessful) {
            val body = result.body()
            val posts = body?.map { it.toPost() }
            if (!posts.isNullOrEmpty()) DataState.Success(posts) else DataState.Empty
        } else {
            DataState.Error(Throwable("HTTP error code: ${result.code()}"))
        }
    } catch (e: Exception) {
        DataState.Error(e)
    }

    suspend fun getComments(postId: Int): DataState<CommentsList> = try {
        val result = postApiService.getComments(postId)

        if (result.isSuccessful) {
            val body = result.body()
            val comments = body?.map { it.toComment() }

            if (!comments.isNullOrEmpty()) DataState.Success(comments) else DataState.Empty
        } else {
            DataState.Error(Throwable("HTTP error code: ${result.code()}"))
        }
    } catch (e: Exception) {
        DataState.Error(e)
    }
}