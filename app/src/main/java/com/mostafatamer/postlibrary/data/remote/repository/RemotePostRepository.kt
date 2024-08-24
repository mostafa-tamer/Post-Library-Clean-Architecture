package com.mostafatamer.postlibrary.data.remote.repository

import com.mostafatamer.postlibrary.data.remote.handleApiResponse
import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import javax.inject.Inject

class RemotePostRepository @Inject constructor(
    private val postApiService: PostApiService,
) {
    suspend fun getPosts(): DataState<PostList> = try {
        val result = postApiService.getPosts()
        handleApiResponse(result) { it.map { body -> body.toPost() } }
    } catch (e: Exception) {
        DataState.Error(e)
    }

    suspend fun getComments(postId: Int): DataState<CommentsList> = try {
        val result = postApiService.getComments(postId)
        handleApiResponse(result) { it.map { body -> body.toComment() } }
    } catch (e: Exception) {
        DataState.Error(e)
    }
}