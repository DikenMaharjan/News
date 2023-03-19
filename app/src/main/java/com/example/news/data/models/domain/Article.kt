package com.example.news.data.models.domain

data class Article(
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val sourceName: String,
    val author: String?,
    val title: String,
    val imageURL: String?,
    val url: String
)