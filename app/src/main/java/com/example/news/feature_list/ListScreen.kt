package com.example.news.feature_list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.news.data.models.domain.Article
import com.example.news.feature_list.composables.CategoriesRow
import com.example.news.feature_list.composables.TopArticles
import com.example.news.feature_list.composables.categoryArticles


private const val TAG = "ListScreen"

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel(),
    navigateToDetail: (article: Article) -> Unit,
) {
    val topArticles = viewModel.topArticles.collectAsLazyPagingItems()
    val categoryArticles = viewModel.categoryArticles.collectAsLazyPagingItems()
    val loading by remember {
        derivedStateOf {
            topArticles.loadState.refresh is LoadState.Loading && viewModel.showRefresh
        }
    }

    val state = rememberPullRefreshState(
        refreshing = loading,
        onRefresh = {
            topArticles.refresh()
            categoryArticles.refresh()
            viewModel.changeShowRefresh(true)
        }
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .pullRefresh(state)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                item {
                    Text(
                        text = "Top News",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                item {
                    TopArticles(
                        articles = topArticles,
                        onArticleClick = navigateToDetail
                    )
                }

                item {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                stickyHeader {
                    CategoriesRow(
                        selectCategory = viewModel::selectCategory,
                        selectedCategory = viewModel.selectedCategory
                    )
                }
                categoryArticles(articles = categoryArticles, onArticleClick = navigateToDetail)
            }
            val mediatorStateCategoryArticles = categoryArticles.loadState.mediator?.refresh
            if (mediatorStateCategoryArticles is LoadState.Error) {
                Text(
                    text = mediatorStateCategoryArticles.error.message ?: "Something went wrong",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
        PullRefreshIndicator(
            refreshing = loading, state = state, modifier = Modifier.align(
                Alignment.TopCenter
            )
        )
    }
}
