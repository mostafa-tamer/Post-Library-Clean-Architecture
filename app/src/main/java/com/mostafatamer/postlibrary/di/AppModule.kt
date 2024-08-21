package com.mostafatamer.postlibrary.di

import android.content.Context
import androidx.room.Room
import com.mostafatamer.postlibrary.data.local.AppDatabase
import com.mostafatamer.postlibrary.data.local.dao.CommentDao
import com.mostafatamer.postlibrary.data.local.dao.FavoritePostDao
import com.mostafatamer.postlibrary.data.local.dao.PostDao
import com.mostafatamer.postlibrary.data.local.repositoty.LocalPostRepository
import com.mostafatamer.postlibrary.data.remote.repository.RemotePostRepository
import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    fun provideRetrofitInstance(baseUrl: String): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun providePostApiService(retrofit: Retrofit): PostApiService =
        retrofit.create(PostApiService::class.java)

    @Provides
    @Singleton
    fun provideRemotePostRepository(apiService: PostApiService) = RemotePostRepository(apiService)

    @Provides
    @Singleton
    fun provideLocalPostRepository(
        postDao: PostDao,
        commentDao: CommentDao,
        favoriteDao: FavoritePostDao,
    ) = LocalPostRepository(postDao, commentDao, favoriteDao)

    @Provides
    @Singleton
    fun providePostDao(database: AppDatabase) = database.postDao()

    @Provides
    @Singleton
    fun provideCommentDao(database: AppDatabase) = database.commentDao()

    @Provides
    @Singleton
    fun provideFavoritePostDao(database: AppDatabase) = database.favoritePostDao()

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }
}