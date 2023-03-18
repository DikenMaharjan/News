package com.example.news.feature_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel(),
    navigateToDetail: () -> Unit,
) {
    val articles = viewModel.articles.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
    ) {
        items(articles) { article ->
            article?.let {
                Text(text = it.title)
            }
        }
    }
}