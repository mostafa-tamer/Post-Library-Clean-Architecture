package com.mostafatamer.postlibrary.data.remote.service

import com.mostafatamer.postlibrary.data.remote.dto.PostDto
import com.mostafatamer.postlibrary.domain.model.PostDtoList
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface MockPostApiService {

    @GET("/posts/favorites")
    suspend fun getFavoritePosts(): Response<PostDtoList>

    @POST("/posts/isFavorite")
    suspend fun isFavoritePost(@Body post: PostDto): Response<Boolean>

    @POST("/posts/addPostsToFavorite")
    suspend fun addPostsToFavorites(@Body post: List<PostDto>): Response<List<PostDto>>

    @POST("/posts/addPostToFavorite")
    suspend fun addPostToFavorites(@Body post: PostDto): Response<PostDto>

    @POST("/posts/removePostToFavorite")
    suspend fun removePostFromFavorites(@Body post: PostDto): Response<PostDto>
}