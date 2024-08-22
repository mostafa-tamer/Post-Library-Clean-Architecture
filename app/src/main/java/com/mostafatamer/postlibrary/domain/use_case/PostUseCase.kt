package com.mostafatamer.postlibrary.domain.use_case

import com.mostafatamer.postlibrary.data.local.repositoty.LocalPostRepository
import com.mostafatamer.postlibrary.data.remote.repository.RemotePostRepository
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val remotePostRepository: RemotePostRepository,
    private val localPostRepository: LocalPostRepository,
) {

    suspend fun getPosts(): DataState<PostList> {
        val posts = remotePostRepository.getPosts()

        return if (posts is DataState.Success) {
            localPostRepository.savePosts(posts.data)
            DataState.Success(posts.data)
        } else {
            posts
        }
    }

    suspend fun getSavedPosts(): DataState<PostList> =
        localPostRepository.getPosts()

    suspend fun getSavedComments(postId: Int): DataState<CommentsList> {
        return localPostRepository.getComments(postId)
    }

    suspend fun getComments(postId: Int): DataState<CommentsList> {
        val comments = remotePostRepository.getComments(postId)

        return if (comments is DataState.Success) {
            localPostRepository.saveComments(comments.data)
            DataState.Success(comments.data)
        } else {
            comments
        }
    }

    fun isFavoritePost(postId: Int): Flow<Boolean> {
        return localPostRepository.isFavoritePost(postId)
    }

    suspend fun addToFavoritePost(postId: Int) {
        localPostRepository.addToFavoritePost(postId)
    }

    suspend fun removeFromFavoritePost(postId: Int) {
        localPostRepository.removeFromFavoritePost(postId)
    }

    suspend fun getPostById(postId: Int): DataState.Success<Post> {
        val postState = localPostRepository.getPostById(postId)
        return if (postState is DataState.Success) {
            DataState.Success(postState.data)
        } else {
            throw IllegalStateException("Post not found")
        }
    }

    suspend fun getFavoritePosts(): DataState<PostList> = localPostRepository.getFavoritePosts()
}