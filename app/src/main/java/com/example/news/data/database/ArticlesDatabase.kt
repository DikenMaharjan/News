package com.example.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.news.data.database.dao.ArticlesDao
import com.example.news.data.models.local.ArticleEntity

@Database(
    entities = [
        ArticleEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class ArticlesDatabase : RoomDatabase() {
    abstract fun articlesDao(): ArticlesDao
}