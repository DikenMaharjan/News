package com.example.news.data.models.mappers

import com.example.news.data.models.domain.Article
import com.example.news.data.models.local.ArticleEntity
import com.example.news.data.models.response.fetchnewsresponse.ArticleDto


fun ArticleDto.toEntity(fetchedFor: String) = ArticleEntity(
    author = this.author,
    content = this.content,
    description = this.description,
    publishedAt = this.publishedAt,
    sourceID = this.source.id,
    sourceName = this.source.name,
    title = this.title,
    url = this.url,
    urlToImage = this.urlToImage,
    fetchedFor = fetchedFor
)

fun ArticleEntity.toModel() = Article(
    author = this.author,
    title = this.title,
    imageURL = this.urlToImage,
    url = this.url
)