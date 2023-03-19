package com.example.news.data.models.mappers

import com.example.news.data.models.domain.Article
import com.example.news.data.models.local.ArticleEntity
import com.example.news.data.models.response.fetchnewsresponse.ArticleDto


fun ArticleDto.toEntity(fetchedFor: String) = ArticleEntity(
    author = this.author,
    content = this.content,
    publishedAt = this.publishedAt,
    title = this.title,
    url = this.url,
    urlToImage = this.urlToImage,
    fetchedFor = fetchedFor
)

fun ArticleEntity.toModel() = Article(
    author = this.author,
    title = this.title,
    imageURL = this.urlToImage,
    url = this.url,
    content = this.content,
    publishedAt = this.publishedAt,
)