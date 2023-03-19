package com.example.news.navigation.routes

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.news.navigation.ScreenRoute

//todo add nav-arguments to the destination

object DetailRoute : ScreenRoute() {
    override val route: String
        get() = "$routePrefix/{$ARTICLE_URL_KEY}"
    override val routePrefix: String
        get() = "detail_route"

    override fun getArguments(): List<NamedNavArgument> {
        return listOf(
            navArgument(ARTICLE_URL_KEY) {
                this.type = NavType.StringType
                this.nullable = false
            }
        )
    }

    fun SavedStateHandle.getArticleURL(): String = Uri.decode(this.get<String>(ARTICLE_URL_KEY)!!)

    fun createNavigationRoute(articleUrl: String): String {
        return "$routePrefix/${Uri.encode(articleUrl)}"
    }

    private const val ARTICLE_URL_KEY = "ArticleUrlID"
}