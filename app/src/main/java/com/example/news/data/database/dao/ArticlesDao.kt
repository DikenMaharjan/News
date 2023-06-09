package com.example.news.data.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.example.news.data.models.local.ArticleEntity
import kotlinx.coroutines.flow.Flow

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

    @Query("Delete from ArticleEntity where fetchedFor = :fetchedFor")
    suspend fun deleteArticlesFetchedFor(fetchedFor: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeArticles(articles: List<ArticleEntity>)

    @Query("Select * from ArticleEntity where url = :articleURL")
    fun getArticle(articleURL: String): Flow<ArticleEntity?>
}