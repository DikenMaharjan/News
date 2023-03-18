package com.example.news.feature_list

import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news.data.models.domain.Article
import com.example.news.data.repo.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val TAG = "ListScreenViewModel"

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    articlesRepository: ArticlesRepository
) : ViewModel() {
    var selectedCategory by mutableStateOf(ArticlesRepository.AvailableCategories.General)
        private set

    private val categoryArticlesMap =
        mutableMapOf<ArticlesRepository.AvailableCategories, Flow<PagingData<Article>>>()

    val categoryArticles: Flow<PagingData<Article>> by derivedStateOf {
        categoryArticlesMap[selectedCategory] ?: kotlin.run {
            categoryArticlesMap.getOrPut(
                selectedCategory
            ) {
                articlesRepository.getArticlesPagingData(
                    fetchFor = ArticlesRepository.FetchArticlesFor.Category(selectedCategory)
                ).cachedIn(viewModelScope)
            }
        }
    }

    val topArticles = articlesRepository.getArticlesPagingData(
        fetchFor = ArticlesRepository.FetchArticlesFor.Top
    ).cachedIn(viewModelScope)

    fun selectCategory(category: ArticlesRepository.AvailableCategories) {
        selectedCategory = category
    }
}