package com.example.news.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.screen(
    screenRoute: ScreenRoute,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = screenRoute.route,
        arguments = screenRoute.getArguments(),
        content = content
    )
}