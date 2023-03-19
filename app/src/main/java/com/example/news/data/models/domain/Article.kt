package com.example.news.data.models.domain

data class Article(
    val id: Long,
    val author: String?,
    val content: String?,
    val description: String?,
    val title: String,
    val imageURL: String?
)