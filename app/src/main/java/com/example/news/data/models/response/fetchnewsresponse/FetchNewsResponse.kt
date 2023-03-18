package com.example.news.data.models.response.fetchnewsresponse

data class FetchNewsResponse(
    val articles: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)