package com.example.news.feature_list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.news.feature_list.composables.TopArticleItem


private const val TAG = "ListScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel(),
    navigateToDetail: () -> Unit,
) {
    val topArticles = viewModel.topArticles.collectAsLazyPagingItems()
    LazyColumn(
        modifier = modifier.fillMaxSize(),
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
            LazyRow(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 12.dp),
            ) {
                items(topArticles) { article ->
                    article?.let {
                        TopArticleItem(
                            article = article
                        )
                    }
                }
            }
        }

        item {
            Text(
                text = "Categories",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 48.dp)
            )
        }

        item {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                ListScreenViewModel.AvailableCategories.values().forEach {
                    FilterChip(
                        modifier = Modifier.padding(end = 12.dp), onClick = {
                            viewModel.selectCategory(it)
                        }, label = {
                            Text(text = it.title)
                        }, selected = viewModel.selectedCategory == it
                    )
                }
            }
        }
    }
}
