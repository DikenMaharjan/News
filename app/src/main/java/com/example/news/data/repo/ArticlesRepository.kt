package com.example.news.data.repo

import com.example.news.data.models.response.FetchNewsResponse
import com.example.news.data.remote.ArticlesApi
import com.example.news.network.Resource
import com.example.news.network.SafeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticlesRepository @Inject constructor(
    private val articlesApi: ArticlesApi,
    private val safeApiCall: SafeApiCall
) {
    suspend fun getNews(): Resource<FetchNewsResponse> {
        return safeApiCall.execute {
            articlesApi.getNews()
        }
    }
}