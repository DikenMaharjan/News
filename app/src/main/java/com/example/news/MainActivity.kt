package com.example.news

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.news.feature_detail.DetailScreen
import com.example.news.feature_list.ListScreen
import com.example.news.navigation.routes.DetailRoute
import com.example.news.navigation.routes.ListRoute
import com.example.news.navigation.screen
import com.example.news.ui.theme.NewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = ListRoute.route
                    ) {
                        screen(
                            screenRoute = ListRoute
                        ) {
                            ListScreen(
                                navigateToDetail = {
                                    navController.navigate(DetailRoute.route)
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
        }
    }
}

