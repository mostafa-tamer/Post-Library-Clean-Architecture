package com.mostafatamer.postlibrary.api_service

import com.google.gson.Gson
import com.mostafatamer.postlibrary.CommentsResponse
import com.mostafatamer.postlibrary.PostsResponse
import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PostApiServiceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var postApiService: PostApiService

    private val jsonConverter = Gson()

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        postApiService = retrofit.create(PostApiService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun mock_test_on_get_posts() = runTest {
        val jsonResponse = jsonConverter.toJson(com.mostafatamer.postlibrary.PostsResponse)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonResponse)

        mockWebServer.enqueue(mockResponse)

        val response = postApiService.getPosts()

        assertTrue(response.isSuccessful)
        assertEquals(5, response.body()?.size)
    }

    @Test
    fun mock_test_on_get_comments() = runTest {
        val jsonResponse = jsonConverter.toJson(com.mostafatamer.postlibrary.CommentsResponse)

        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody(jsonResponse)

        mockWebServer.enqueue(mockResponse)

        val response = postApiService.getComments(1)

        assertTrue(response.isSuccessful)
        assertEquals(5, response.body()?.size)
    }
}
