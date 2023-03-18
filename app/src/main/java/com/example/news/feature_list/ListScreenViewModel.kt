package com.example.news.feature_list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.news.data.repo.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ListScreenViewModel"

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    articlesRepository: ArticlesRepository
) : ViewModel() {

    enum class AvailableCategories(val title: String) {
        General("General"),
        Business("Business"),
        Entertainment("Entertainment"),
        Health("Health"),
        Science("Science"),
        Sports("Sports"),
        Technology("Technology")
    }

    var selectedCategory by mutableStateOf(AvailableCategories.General)
        private set

    val topArticles = articlesRepository.getTopArticlesPagingData(
        pageSize = 30
    ).cachedIn(viewModelScope)

    fun selectCategory(category: AvailableCategories) {
        selectedCategory = category
    }
}