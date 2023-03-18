package com.example.news.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.news.data.models.local.ArticleEntity

@Dao
interface ArticlesDao {

    @Query("Select * from ArticleEntity")
    fun getArticlesPagingSource(): PagingSource<Int, ArticleEntity>

    @Query("Select count(*) from ArticleEntity")
    suspend fun getArticlesCount(): Int

    @Query("Delete from ArticleEntity")
    suspend fun clear()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeArticles(articles: List<ArticleEntity>)
}