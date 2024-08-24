package com.mostafatamer.postlibrary.domain.use_case

import com.mostafatamer.postlibrary.data.local.entity.FavoritePostToSyncEntity
import com.mostafatamer.postlibrary.data.local.repositoty.FavoritePostToSyncRepository
import com.mostafatamer.postlibrary.data.local.repositoty.LocalPostRepository
import com.mostafatamer.postlibrary.data.remote.repository.MockRemotePostRepository
import com.mostafatamer.postlibrary.data.remote.repository.RemotePostRepository
import com.mostafatamer.postlibrary.domain.model.CommentsList
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class PostUseCase @Inject constructor(
    private val remotePostRepository: RemotePostRepository,
    private val localPostRepository: LocalPostRepository,
    private val favoritePostToSyncRepository: FavoritePostToSyncRepository,
    private val mockRemotePostRepository: MockRemotePostRepository,
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

    suspend fun isFavoritePost(post: Post): DataState<Boolean> {
        return mockRemotePostRepository.isFavoritePost(post)
    }

    suspend fun addToFavoritePost(post: Post) {
        val result = mockRemotePostRepository.savePostToFavorites(post)

        if (result is DataState.Error) {
            favoritePostToSyncRepository.insert(
                FavoritePostToSyncEntity(post.id)
            )
        }

    }

    suspend fun removeFromFavoritePost(post: Post): DataState<Post> {
        val result = mockRemotePostRepository.removePostFromFavorites(post)

        return result
    }

    suspend fun getPostById(postId: Int): DataState.Success<Post> {
        val postState = localPostRepository.getPostById(postId)
        return if (postState is DataState.Success) {
            DataState.Success(postState.data)
        } else {
            throw IllegalStateException("Post not found")
        }
    }

    suspend fun loadFavoritePosts(): DataState<PostList> =
        mockRemotePostRepository.loadFavoritePosts()

    suspend fun syncFavoritePosts(): DataState<PostList> {
        val favoritePostsToBeSynced = favoritePostToSyncRepository.getAll()
            .first()
            .map { it.toPost() }

        val responseState = mockRemotePostRepository.savePostsToFavorites(favoritePostsToBeSynced)

        if (responseState is DataState.Success) {
            favoritePostToSyncRepository.deleteAll()
        }

        return responseState
    }
}