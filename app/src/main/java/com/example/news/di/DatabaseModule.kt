package com.example.news.di

import android.content.Context
import androidx.room.Room
import com.example.news.data.database.ArticlesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATABASE_NAME = "ArticlesDatabase"

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {
    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext appContext: Context
    ) = Room.databaseBuilder(
        appContext,
        ArticlesDatabase::class.java,
        DATABASE_NAME
    )

    @Singleton
    @Provides
    fun providesArticlesDao(database: ArticlesDatabase) = database.articlesDao()
}