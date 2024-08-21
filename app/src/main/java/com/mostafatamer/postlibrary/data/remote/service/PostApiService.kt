package com.mostafatamer.postlibrary.data.remote.service

import com.mostafatamer.postlibrary.domain.model.CommentsDtoList
import com.mostafatamer.postlibrary.domain.model.PostDtoList
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Path


interface PostApiService {

    @GET("/posts")
    suspend fun getPosts(): Response<PostDtoList>

    @GET("/posts/{post_id}/comments")
    suspend fun getComments(@Path("post_id") postId: Int): Response<CommentsDtoList>
}