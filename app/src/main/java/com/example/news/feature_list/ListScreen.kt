package com.example.news.feature_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.feature_list.composables.CategoriesRow
import com.example.news.feature_list.composables.TopArticles
import com.example.news.feature_list.composables.categoryArticles


private const val TAG = "ListScreen"

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel(),
    navigateToDetail: () -> Unit,
) {
    val topArticles = viewModel.topArticles.collectAsLazyPagingItems()
    val categoryArticles = viewModel.categoryArticles.collectAsLazyPagingItems()

    val showPullRefresh by remember {
        derivedStateOf {
            topArticles.loadState.refresh is LoadState.Loading
        }
    }

    val state = rememberPullRefreshState(
        refreshing = showPullRefresh,
        onRefresh = {
            topArticles.refresh()
            categoryArticles.refresh()
        }
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(12.dp)
        ) {
            item {
                Text(
                    text = "Top News",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            item {
                TopArticles(articles = topArticles)
            }

            item {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 48.dp)
                )
            }

            stickyHeader {
                CategoriesRow(
                    selectCategory = viewModel::selectCategory,
                    selectedCategory = viewModel.selectedCategory
                )
            }
            categoryArticles(articles = categoryArticles)
        }
        PullRefreshIndicator(
            refreshing = showPullRefresh, state = state, modifier = Modifier.align(
                Alignment.TopCenter
            )
        )
    }
}
