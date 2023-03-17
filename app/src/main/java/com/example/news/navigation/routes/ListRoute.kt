package com.example.news.navigation.routes

import com.example.news.navigation.ScreenRoute

object ListRoute: ScreenRoute() {
    override val route: String
        get() = routePrefix
    override val routePrefix: String
        get() = "main_route"
}