package com.example.news.navigation.routes

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.news.navigation.ScreenRoute

//todo add nav-arguments to the destination

object DetailRoute : ScreenRoute() {
    override val route: String
        get() = "$routePrefix/{$ARTICLE_ID_KEY}"
    override val routePrefix: String
        get() = "detail_route"

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(ARTICLE_ID_KEY) {
                this.type = NavType.LongType
                this.nullable = false
            }
        )
    }

    fun createNavigationRoute(articleID: Long): String {
        return "$routePrefix/$articleID"
    }

    private const val ARTICLE_ID_KEY = "ArticleID"
}