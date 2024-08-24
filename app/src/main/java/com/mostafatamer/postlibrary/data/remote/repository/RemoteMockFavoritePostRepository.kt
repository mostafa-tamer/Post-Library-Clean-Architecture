package com.mostafatamer.postlibrary.data.remote.repository

import com.google.gson.Gson
import com.mostafatamer.postlibrary.data.local.dao.MockServerFavoritePostDao
import com.mostafatamer.postlibrary.data.remote.handleApiResponse
import com.mostafatamer.postlibrary.data.remote.hasInternetAccess
import com.mostafatamer.postlibrary.data.remote.service.MockPostApiService
import com.mostafatamer.postlibrary.domain.model.Post
import com.mostafatamer.postlibrary.domain.model.PostList
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class RemoteMockFavoritePostRepository @Inject constructor(
    private val mockServerFavoritePostDao: MockServerFavoritePostDao,
    private val mockWebServer: MockWebServer,
    @Named("mockWebServerRetrofit")
    private val mockWebServerRetrofit: Lazy<Retrofit>,
) {
    //Injection can not be done through constructor because "mockWebServerRetrofit" needs a non-main thread context
    private val mockPostApiService by lazy { mockWebServerRetrofit.value.create(MockPostApiService::class.java) }

    private val jsonConverter = Gson()

    suspend fun savePostToFavorites(post: Post): DataState<Post> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            mockServerFavoritePostDao.addToFavorites(post.toMockServerPostEntity())

            val postDtoBody = post.toPostDto()

            val jsonResponse = jsonConverter.toJson(postDtoBody)

            triggerMockRequest(jsonResponse)

            val result = mockPostApiService.addPostToFavorites(postDtoBody)

            handleApiResponse(result) { postDto -> postDto.toPost() }

        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun isFavoritePost(post: Post): DataState<Boolean> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            val isFavoritePost = mockServerFavoritePostDao.isFavorite(post.id)

            val jsonResponse = jsonConverter.toJson(isFavoritePost.first())

            triggerMockRequest(jsonResponse)

            val postDao = post.toPostDto()

            val result = mockPostApiService.isFavoritePost(postDao)

            handleApiResponse(result) { it }
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun removePostFromFavorites(post: Post): DataState<Post> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            mockServerFavoritePostDao.removeFromFavorites(post.toMockServerPostEntity())

            val postDtoBody = post.toPostDto()

            val jsonResponse = jsonConverter.toJson(postDtoBody)

            triggerMockRequest(jsonResponse)

            val result = mockPostApiService.removePostFromFavorites(postDtoBody)

            handleApiResponse(result) { postDto -> postDto.toPost() }
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    suspend fun savePostsToFavorites(post: List<Post>): DataState<PostList> =
        withContext(Dispatchers.IO) {
            try {
                if (!hasInternetAccess())
                    return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

                val favoritePostsEntity = post.map { it.toMockServerPostEntity() }

                mockServerFavoritePostDao.addToFavorites(favoritePostsEntity)

                val favoritePostsDto = post.map { it.toPostDto() }

                val jsonResponse = jsonConverter.toJson(favoritePostsDto)

                triggerMockRequest(jsonResponse)

                val result = mockPostApiService.addPostsToFavorites(favoritePostsDto)

                handleApiResponse(result) { it.map { body -> body.toPost() } }
            } catch (e: Exception) {
                DataState.Error(e)
            }
        }


    suspend fun loadFavoritePosts(): DataState<PostList> = withContext(Dispatchers.IO) {
        try {
            if (!hasInternetAccess())
                return@withContext DataState.Error(Throwable(DataState.Error.noInternetErrorMessage()))

            val jsonResponse = jsonConverter.toJson(mockServerFavoritePostDao.getAllFavoritePosts())

            triggerMockRequest(jsonResponse)

            val result = mockPostApiService.getFavoritePosts()

            handleApiResponse(result) { it.map { body -> body.toPost() } }
        } catch (e: Exception) {
            DataState.Error(e)
        }
    }

    private suspend fun triggerMockRequest(jsonResponse: String) {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonResponse)

        mockWebServer.enqueue(mockResponse)

        delay(350)  //delay simulation
    }
}