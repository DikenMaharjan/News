package com.example.news.feature_list

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

    val topArticles = articlesRepository.getTopArticlesPagingData(
        pageSize = 30
    ).cachedIn(viewModelScope)
}