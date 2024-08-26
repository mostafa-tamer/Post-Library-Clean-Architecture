package com.mostafatamer.postlibrary.data.remote.repository

import com.mostafatamer.postlibrary.data.local.dao.MockServerFavoritePostDao
import com.mostafatamer.postlibrary.data.remote.hasInternetAccess
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteMockFavoritePostRepository @Inject constructor(
    private val mockServerFavoritePostDao: MockServerFavoritePostDao,
) {

    suspend fun savePostToFavorites(post: Post): DataState<Post> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            mockServerFavoritePostDao.addToFavorites(post.toMockServerPostEntity())

            simulateNetworkDelay()

            DataState.Success(post)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun simulateNetworkDelay() {
        delay(400)
    }

    suspend fun isFavoritePost(post: Post): DataState<Boolean> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            val isFavoritePost = mockServerFavoritePostDao.isFavorite(post.id)

            simulateNetworkDelay()

            DataState.Success(isFavoritePost.first())
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun removePostFromFavorites(post: Post): DataState<Post> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            mockServerFavoritePostDao.removeFromFavorites(post.toMockServerPostEntity())

            simulateNetworkDelay()

            DataState.Success(post)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun savePostsToFavorites(postList: List<Post>): DataState<PostList> =
        withContext(Dispatchers.IO) {
            try {
                if (!hasInternetAccess())
                    return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

                val favoritePostsEntity = postList.map { it.toMockServerPostEntity() }

                mockServerFavoritePostDao.addToFavorites(favoritePostsEntity)

                simulateNetworkDelay()

                DataState.Success(postList)
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }


    suspend fun loadFavoritePosts(): DataState<PostList> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            val favoritePosts = mockServerFavoritePostDao.getAllFavoritePosts()
                .map { it.toPost() }

            simulateNetworkDelay()

            if (favoritePosts.isEmpty()) DataState.Empty else DataState.Success(favoritePosts)
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }
}