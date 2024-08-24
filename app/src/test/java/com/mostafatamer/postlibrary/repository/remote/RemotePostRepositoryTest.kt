package com.mostafatamer.postlibrary.repository.remote

import com.mostafatamer.postlibrary.CommentsResponse
import com.mostafatamer.postlibrary.PostsResponse
import com.mostafatamer.postlibrary.data.remote.repository.RemotePostRepository
import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import com.mostafatamer.postlibrary.domain.state.DataState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class RemotePostRepositoryTest {

    private lateinit var postApiService: PostApiService
    private lateinit var remotePostRepository: RemotePostRepository

    @Before
    fun setUp() {
        postApiService = mockk<PostApiService>()
        remotePostRepository = RemotePostRepository(postApiService)
    }

    @Test
    fun test_getPosts_when_exception_occurs() = runTest {
        val exceptionMessage = "Network error"

        coEvery { postApiService.getPosts() } throws IOException(exceptionMessage)

        val result = remotePostRepository.getPosts()

        assertTrue(result is DataState.Error)
        assertEquals(
            (result as DataState.Error).exception.message,
            exceptionMessage
        )
    }

    @Test
    fun test_getPosts_when_unsuccessful_request_with_error_status_code() = runTest {
        coEvery { postApiService.getPosts() } returns Response.error(500, mockk(relaxed = true))

        val result = remotePostRepository.getPosts()

        assertTrue(result is DataState.Error)
        assertEquals(
            (result as DataState.Error).exception.message,
            Throwable(DataState.Error.unSuccessfulResponseErrorMessage(500)).message
        )
    }

    @Test
    fun test_getPosts_when_successful_request() = runTest {
        val mockPosts = PostsResponse

        coEvery { postApiService.getPosts() } returns Response.success(mockPosts)

        val resultSuccess = remotePostRepository.getPosts()

        assertTrue(resultSuccess is DataState.Success)
        assertEquals((resultSuccess as DataState.Success).data, mockPosts.map { it.toPost() })
    }

    @Test
    fun test_get_comments_when_successful_request() =
        runTest {
            val mockComments = com.mostafatamer.postlibrary.CommentsResponse

            coEvery { postApiService.getComments(1) } returns Response.success(mockComments)

            val result = remotePostRepository.getComments(1)

            assertTrue(result is DataState.Success)
            assertEquals(mockComments.map { it.toComment() }, (result as DataState.Success).data)
        }

    @Test
    fun test_get_comments_when_unsuccessful_request_with_error_status_code() = runTest {
        coEvery { postApiService.getComments(1) } returns Response.error(500, mockk(relaxed = true))

        val result = remotePostRepository.getComments(1)

        assertTrue(result is DataState.Error)
        assertEquals(
            DataState.Error.unSuccessfulResponseErrorMessage(500),
            (result as DataState.Error).exception.message
        )
    }

    @Test
    fun test_get_comments_when_exception_occurs() = runTest {
        val exceptionMessage = "Network error"

        coEvery { postApiService.getComments(1) } throws IOException(exceptionMessage)

        val result = remotePostRepository.getComments(1)

        assertTrue(result is DataState.Error)
        assertEquals(exceptionMessage, (result as DataState.Error).exception.message)
    }
}
