package com.example.news.navigation

import androidx.navigation.NamedNavArgument

abstract class ScreenRoute {

    abstract val route: String
    protected abstract val routePrefix: String

    open fun getArguments(): List<NamedNavArgument> {
        return emptyList()
    }
}