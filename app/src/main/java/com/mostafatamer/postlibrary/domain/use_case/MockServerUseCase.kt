package com.mostafatamer.postlibrary.domain.use_case

import com.mostafatamer.postlibrary.data.local.entity.FavoritePostToSyncEntity
import com.mostafatamer.postlibrary.data.local.repositoty.LocalFavoritePostToSyncRepository
import com.mostafatamer.postlibrary.data.remote.repository.RemoteMockFavoritePostRepository
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class MockServerUseCase @Inject constructor(
    private val localFavoritePostToSyncRepository: LocalFavoritePostToSyncRepository,
    private val remoteMockFavoritePostRepository: RemoteMockFavoritePostRepository,
) {

    suspend fun isFavoritePost(post: Post): DataState<Boolean> {
        return remoteMockFavoritePostRepository.isFavoritePost(post)
    }

    suspend fun addToFavoritePost(post: Post) {
        val result = remoteMockFavoritePostRepository.savePostToFavorites(post)

        if (result is DataState.Error) {
            localFavoritePostToSyncRepository.insert(
                FavoritePostToSyncEntity(post.id)
            )
        }
    }

    suspend fun removeFromFavoritePost(post: Post): DataState<Post> {
        val result = remoteMockFavoritePostRepository.removePostFromFavorites(post)

        return result
    }


    suspend fun loadFavoritePosts(): DataState<PostList> =
        remoteMockFavoritePostRepository.loadFavoritePosts()

    suspend fun syncFavoritePosts(): DataState<PostList> {
        val favoritePostsToBeSynced = localFavoritePostToSyncRepository.getAll()
            .first()
            .map { it.toPost() }

        val responseState =
            remoteMockFavoritePostRepository.savePostsToFavorites(favoritePostsToBeSynced)

        if (responseState is DataState.Success) {
            localFavoritePostToSyncRepository.deleteAll()
        }

        return responseState
    }
}