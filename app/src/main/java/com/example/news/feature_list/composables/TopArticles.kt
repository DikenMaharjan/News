package com.example.news.feature_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.ui.theme.DarkColors
import com.example.news.utils.shimmer.Shimmer

private const val TAG = "TopArticles"

@Composable
fun TopArticles(
    modifier: Modifier = Modifier,
    articles: LazyPagingItems<Article>,
    onArticleClick: (article: Article) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 12.dp)
    ) {
        val mediatorRefreshState = articles.loadState.mediator?.refresh
        val sourceRefreshState = articles.loadState.source.refresh
        LazyRow(
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (articles.itemCount == 0) {
                if (mediatorRefreshState is LoadState.Loading) {
                    repeat(5) {
                        item {
                            ShimmeringTopArticleItem()
                        }
                    }
                } else if (sourceRefreshState is LoadState.NotLoading && mediatorRefreshState is LoadState.NotLoading) {
                    item {
                        Text(
                            text = "No news at the moment",
                            modifier = Modifier.fillParentMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                items(articles) { article ->
                    article?.let {
                        TopArticleItem(
                            article = article, onArticleClick = onArticleClick
                        )
                    }
                }
            }
            if (articles.loadState.append is LoadState.Loading) {
                item {
                    Box(modifier = Modifier.fillParentMaxWidth()) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(18.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
        if (mediatorRefreshState is LoadState.Error) {
            Text(
                text = mediatorRefreshState.error.message ?: "Something went wrong",
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
fun TopArticleItem(
    modifier: Modifier = Modifier, article: Article, onArticleClick: (article: Article) -> Unit
) {
    StackLayout(modifier = modifier
        .padding(end = 12.dp)
        .height(152.dp)
        .fillMaxHeight()
        .clip(MaterialTheme.shapes.extraSmall)
        .clickable {
            onArticleClick(article)
        }, backgroundContent = {
        AsyncImage(
            model = article.imageURL,
            contentScale = ContentScale.FillHeight,
            contentDescription = "ArticleImage",
            modifier = Modifier.fillMaxWidth()
        )
    }, foregroundContent = {
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

    })
}

private fun Modifier.gradientOverlay() = this.then(
    background(
        Brush.verticalGradient(
            0f to Color.Transparent, 1f to Color.Black
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
    Layout(modifier = modifier, content = {
        backgroundContent()
        foregroundContent()
    }) { measurables, constraints ->
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


