package com.example.news.data.remote

import com.example.news.data.models.response.fetchnewsresponse.FetchNewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ArticlesApi {

    @GET("/v2/top-headlines")
    suspend fun getNews(
        @Query("country") country: String = "us",
        @Query("page") page: Int = 1,
        @Query("pageSize") pageSize: Int = 20
    ): Response<FetchNewsResponse>

}