package com.example.news.data.remote

import com.example.news.data.models.response.FetchNewsResponse
import retrofit2.Response
import retrofit2.http.GET


interface ArticlesApi {

    @GET("/v2/everything")
    suspend fun getNews(): Response<FetchNewsResponse>

}