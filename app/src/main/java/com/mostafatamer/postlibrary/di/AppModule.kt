package com.mostafatamer.postlibrary.di

import android.content.Context
import android.net.ConnectivityManager
import androidx.room.Room
import com.mostafatamer.postlibrary.data.local.AppDatabase
import com.mostafatamer.postlibrary.data.remote.service.PostApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = "https://jsonplaceholder.typicode.com/"

    @Provides
    @Singleton
    @Named("retrofit")
    fun provideRetrofitInstance(
        baseUrl: String,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()

    @Provides
    @Singleton
    fun provideMockWebServer() = MockWebServer()

    @Provides
    @Singleton
    fun providePostApiService(@Named("retrofit") retrofit: Retrofit): PostApiService =
        retrofit.create(PostApiService::class.java)

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
    fun provideFavoritePostToSyncDao(database: AppDatabase) = database.favoritePostToSyncDao()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    @Named("mockWebServerRetrofit")
    fun provideMockRetrofitInstance(
        mockWebServer: MockWebServer,
        gsonConverterFactory: GsonConverterFactory,
    ): Lazy<Retrofit> = lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideConnectionManager(@ApplicationContext context: Context) =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}