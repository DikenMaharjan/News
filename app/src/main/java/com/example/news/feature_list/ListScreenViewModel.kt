package com.example.news.feature_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.data.repo.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "ListScreenViewModel"

@HiltViewModel
class ListScreenViewModel @Inject constructor(
    articlesRepository: ArticlesRepository
) : ViewModel() {

    init {

        viewModelScope.launch {
            articlesRepository.getNews().apply {
                Log.e(TAG, "$this: ")
            }
        }
    }
}