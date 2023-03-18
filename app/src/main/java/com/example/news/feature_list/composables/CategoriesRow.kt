package com.example.news.feature_list.composables

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.news.feature_list.ListScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesRow(
    modifier: Modifier = Modifier,
    selectCategory: (ListScreenViewModel.AvailableCategories) -> Unit,
    selectedCategory: ListScreenViewModel.AvailableCategories
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        ListScreenViewModel.AvailableCategories.values().forEach {
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