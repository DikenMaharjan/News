package com.example.news.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.news.data.models.local.ArticleEntity

@Dao
interface ArticlesDao {

    @Query("Select * from ArticleEntity where fetchedFor = :fetchedFor")
    fun getArticlesPagingSource(
        fetchedFor: String
    ): PagingSource<Int, ArticleEntity>

    @Query("Select count(*) from ArticleEntity where fetchedFor = :fetchedFor")
    suspend fun getArticlesCount(
        fetchedFor: String
    ): Int

    @Query("Delete from ArticleEntity")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeArticles(articles: List<ArticleEntity>)
}