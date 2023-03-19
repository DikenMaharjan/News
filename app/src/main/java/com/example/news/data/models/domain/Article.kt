package com.example.news.data.models.domain

data class Article(
    val content: String?,
    val publishedAt: String,
    val author: String?,
    val title: String,
    val imageURL: String?,
    val url: String
)