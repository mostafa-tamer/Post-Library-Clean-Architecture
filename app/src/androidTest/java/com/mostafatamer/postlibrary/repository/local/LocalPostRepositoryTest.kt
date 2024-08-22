package com.mostafatamer.postlibrary.repository.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mostafatamer.postlibrary.Comments
import com.mostafatamer.postlibrary.Posts
import com.mostafatamer.postlibrary.data.local.AppDatabase
import com.mostafatamer.postlibrary.data.local.dao.CommentDao
import com.mostafatamer.postlibrary.data.local.dao.FavoritePostDao
import com.mostafatamer.postlibrary.data.local.dao.PostDao
import com.mostafatamer.postlibrary.data.local.entity.FavoritePostEntity
import com.mostafatamer.postlibrary.data.local.entity.PostEntity
import com.mostafatamer.postlibrary.data.local.repositoty.LocalPostRepository
import com.mostafatamer.postlibrary.domain.state.DataState
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalPostRepositoryTest {

    private lateinit var database: AppDatabase
    private lateinit var postDao: PostDao
    private lateinit var commentDao: CommentDao
    private lateinit var favoriteDao: FavoritePostDao
    private lateinit var localPostRepository: LocalPostRepository

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()

        postDao = database.postDao()
        commentDao = database.commentDao()
        favoriteDao = database.favoritePostDao()

        localPostRepository = LocalPostRepository(postDao, commentDao, favoriteDao)
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test_getPosts_when_posts_exist() = runTest {
        val postEntities = listOf(
            PostEntity(1, 1, "Test Post", "Test Body"),
            PostEntity(2, 1, "Test Post 2", "Test Body 2")
        )
        postDao.upsert(postEntities)

        val posts = postEntities.map { it.toPost() }

        val result = localPostRepository.getPosts()

        assertTrue(result is DataState.Success)
        assertEquals(posts, (result as DataState.Success).data)
    }

    @Test
    fun test_getPosts_when_no_posts_exist() = runTest {
        val result = localPostRepository.getPosts()

        assertTrue(result is DataState.Empty)
    }

    @Test
    fun test_getComments_when_comments_exist() = runTest {
        localPostRepository.savePosts(Posts)
        localPostRepository.saveComments(Comments)

        val result = localPostRepository.getComments(1)

        assertTrue(result is DataState.Success)
        assertEquals(
            Comments.stream().limit(2).toArray().toList(),
            (result as DataState.Success).data
        )
    }

    @Test
    fun test_getComments_when_no_comments_exist() = runTest {
        val result = localPostRepository.getComments(1)

        assertTrue(result is DataState.Empty)
    }

    @Test
    fun test_savePosts() = runTest {
        localPostRepository.savePosts(Posts)

        val savedPosts = localPostRepository.getPosts()
        assertEquals(Posts, (savedPosts as DataState.Success).data)
    }

   @Test
    fun test_updatePosts() = runTest {
        localPostRepository.savePosts(Posts)

        val savedPosts = localPostRepository.getPosts()
        assertEquals(Posts, (savedPosts as DataState.Success).data)
    }

    @Test
    fun test_saveComments() = runTest {
        localPostRepository.savePosts(Posts)
        localPostRepository.saveComments(Comments)

        val savedComments = localPostRepository.getComments(1)

        assertTrue(savedComments is DataState.Success)
        assertEquals(listOf(Comments[0], Comments[1]), (savedComments as DataState.Success).data)
    }

    @Test
    fun test_addToFavoritePost() = runTest {
        localPostRepository.savePosts(Posts)

        localPostRepository.addToFavoritePost(1)

        val result = localPostRepository.isFavoritePost(1).first()

        assertTrue(result)
    }

    @Test
    fun test_removeFromFavoritePost() = runTest {
        localPostRepository.savePosts(Posts)
        val post = Posts[0]

        favoriteDao.addToFavorites(FavoritePostEntity(post.id))
        localPostRepository.removeFromFavoritePost(1)

        val result = localPostRepository.isFavoritePost(1).first()
        assertTrue(!result)
    }

    @Test
    fun test_getPostById_when_post_exists() = runTest {
        val postEntity = PostEntity(1, 1, "Test Post", "Test Body")
        postDao.upsert(listOf(postEntity))

        val post = postEntity.toPost()

        val result = localPostRepository.getPostById(1)

        assertTrue(result is DataState.Success)
        assertEquals(post, (result as DataState.Success).data)
    }

    @Test
    fun test_getPostById_when_post_does_not_exist() = runTest {
        val result = localPostRepository.getPostById(1)

        assertTrue(result is DataState.Empty)
    }

    @Test
    fun test_getFavoritePosts_when_no_favorite_posts_exist() = runTest {
        val result = localPostRepository.getFavoritePosts()
        assertTrue(result is DataState.Empty)
    }

    @Test
    fun test_getFavoritePosts_when_favorite_posts_exist() = runTest {
        localPostRepository.savePosts(Posts)

        Posts.forEach {
            favoriteDao.addToFavorites(FavoritePostEntity(it.id))
        }

        val result = localPostRepository.getFavoritePosts()

        assertTrue(result is DataState.Success)
        assertEquals((result as DataState.Success).data.size, Posts.size)
    }
}
