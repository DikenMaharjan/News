package com.example.news.feature_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.ui.theme.DarkColors
import com.example.news.utils.shimmer.Shimmer

@Composable
fun ArticlesRow(
    articles: LazyPagingItems<Article>
) {
    val isLoading by remember {
        derivedStateOf {
            articles.loadState.refresh is LoadState.Loading || articles.loadState.source.refresh is LoadState.Loading || articles.loadState.mediator?.refresh is LoadState.Loading
        }
    }
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 12.dp),
    ) {
        if (articles.itemCount == 0 && isLoading) {
            repeat(5) {
                item {
                    ShimmeringTopArticleItem()
                }
            }
        } else {
            items(articles) { article ->
                article?.let {
                    TopArticleItem(
                        article = article
                    )
                }
            }
        }
    }
}


@Composable
fun TopArticleItem(
    modifier: Modifier = Modifier,
    article: Article
) {
    StackLayout(
        modifier = modifier
            .padding(end = 12.dp)
            .height(152.dp)
            .fillMaxHeight()
            .clip(MaterialTheme.shapes.extraSmall),
        backgroundContent = {
            AsyncImage(
                model = article.imageURL,
                contentScale = ContentScale.FillHeight,
                contentDescription = "ArticleImage",
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        foregroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .gradientOverlay()
            ) {
                Text(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(14.dp),
                    text = article.title,
                    color = DarkColors.onBackground,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    )
}

private fun Modifier.gradientOverlay() = this.then(
    background(
        Brush.verticalGradient(
            0f to Color.Transparent,
            1f to Color.Black
        )
    )
)


@Composable
private fun ShimmeringTopArticleItem() {
    Shimmer(
        modifier = Modifier
            .padding(end = 12.dp)
            .height(152.dp)
            .clip(MaterialTheme.shapes.extraSmall)
            .aspectRatio(1.7f)
    )

}

@Composable
private fun StackLayout(
    modifier: Modifier = Modifier,
    backgroundContent: @Composable () -> Unit,
    foregroundContent: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = {
            backgroundContent()
            foregroundContent()
        }
    ) { measurables, constraints ->
        val firstPlaceable = measurables[0].measure(constraints)
        val secondPlaceable = measurables[1].measure(
            constraints.copy(
                maxWidth = firstPlaceable.width
            )
        )
        layout(firstPlaceable.width, firstPlaceable.height) {
            firstPlaceable.place(0, 0)
            secondPlaceable.place(0, 0)
        }
    }
}


