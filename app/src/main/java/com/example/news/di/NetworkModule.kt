package com.example.news.di

import com.example.news.data.remote.ArticlesApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

private const val BASE_URL = "https://newsapi.org/"
private const val API_KEY = "90e5d2ca4abc4dfc8c1d365bb818ca82"


@Module
@InstallIn(SingletonComponent::class)

object NetworkModule {
    @Provides
    @Singleton
    fun providesRetrofit(
        httpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()

    @Provides
    @Singleton
    fun providesArticlesApi(
        retrofit: Retrofit
    ) = retrofit.create(ArticlesApi::class.java)

    @Provides
    @Singleton
    fun providesHttpClient() = OkHttpClient.Builder().addInterceptor { chain ->
        val request = chain.request().newBuilder().addHeader(
            "x-api-key", API_KEY
        ).build()
        chain.proceed(request)
    }.build()
}