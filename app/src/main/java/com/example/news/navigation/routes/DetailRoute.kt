package com.example.news.navigation.routes

import com.example.news.navigation.ScreenRoute

//todo add nav-arguments to the destination

object DetailRoute : ScreenRoute() {
    override val route: String
        get() = routePrefix
    override val routePrefix: String
        get() = "detail_route"

}