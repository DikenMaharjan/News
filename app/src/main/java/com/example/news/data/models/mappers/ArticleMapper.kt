package com.example.news.data.models.mappers

import com.example.news.data.models.domain.Article
import com.example.news.data.models.local.ArticleEntity
import com.example.news.data.models.response.fetchnewsresponse.ArticleDto

fun ArticleDto.toModel() = Article(
    author = this.author,
    content = this.content,
    description = this.description,
    title = this.title,
    imageURL = this.urlToImage
)

fun ArticleDto.toEntity() = ArticleEntity(
    author = this.author,
    content = this.content,
    description = this.description,
    publishedAt = this.publishedAt,
    sourceID = this.source.id,
    sourceName = this.source.name,
    title = this.title,
    url = this.url,
    urlToImage = this.urlToImage
)

fun ArticleEntity.toModel() = Article(
    author = this.author,
    content = this.content,
    description = this.description,
    title = this.title,
    imageURL = this.urlToImage
)