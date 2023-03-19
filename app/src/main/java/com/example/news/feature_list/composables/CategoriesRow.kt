package com.example.news.feature_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.news.data.repo.ArticlesRepository

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesRow(
    modifier: Modifier = Modifier,
    selectCategory: (ArticlesRepository.AvailableCategories) -> Unit,
    selectedCategory: ArticlesRepository.AvailableCategories
) {
    Row(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(vertical = 8.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        ArticlesRepository.AvailableCategories.values().forEach {
            FilterChip(
                modifier = Modifier.padding(end = 12.dp), onClick = {
                    selectCategory(it)
                }, label = {
                    Text(text = it.title)
                }, selected = selectedCategory == it
            )
        }
    }
}