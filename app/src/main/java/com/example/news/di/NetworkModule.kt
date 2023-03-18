package com.example.news.di

import com.example.news.data.remote.ArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/"


@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit() = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun providesArticlesApi(
        retrofit: Retrofit
    ) = retrofit.create(ArticlesApi::class.java)
}