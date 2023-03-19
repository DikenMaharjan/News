package com.example.news.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.news.feature_detail.DetailScreen
import com.example.news.feature_list.ListScreen
import com.example.news.navigation.routes.DetailRoute
import com.example.news.navigation.routes.ListRoute
import com.example.news.navigation.screen


@Composable
fun MainContent(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        NavHost(
            navController = navController,
            startDestination = ListRoute.route
        ) {
            screen(
                screenRoute = ListRoute
            ) {
                ListScreen(
                    navigateToDetail = { article ->
                        navController.navigate(
                            DetailRoute.createNavigationRoute(article.url)
                        )
                    }
                )
            }
            screen(
                screenRoute = DetailRoute
            ) {
                DetailScreen()
            }
        }
    }
}
