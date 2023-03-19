package com.example.news.data.models.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleEntity(
    val fetchedFor: String,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String,
    val sourceID: String?,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}