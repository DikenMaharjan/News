package com.example.news.feature_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.models.domain.Article
import com.example.news.data.repo.ArticlesRepository
import com.example.news.navigation.routes.DetailRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

private const val TAG = "DetailScreenViewModel"

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    articlesRepository: ArticlesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val articleURL = with(DetailRoute) {
        savedStateHandle.getArticleURL()
    }

    val article: StateFlow<Article?> = articlesRepository.getArticleFlow(articleURL).stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(5000), null
    )


}
