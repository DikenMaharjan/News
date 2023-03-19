package com.example.news.feature_list.composables


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.utils.image.TemporaryImageURL
import com.example.news.utils.shimmer.Shimmer
import kotlin.random.Random

fun LazyListScope.categoryArticles(
    articles: LazyPagingItems<Article>,
    onArticleClick: (article: Article) -> Unit
) {
    if (articles.itemCount == 0) {
        val mediatorRefreshState = articles.loadState.mediator?.refresh
        val sourceRefreshState = articles.loadState.source.refresh
        if (mediatorRefreshState is LoadState.Loading) {
            repeat(15) {
                item {
                    ShimmeringCategoryArticle()
                }
            }
        } else if (sourceRefreshState is LoadState.NotLoading && mediatorRefreshState is LoadState.NotLoading) {
            item {
                Text(
                    text = "No news at the moment",
                    modifier = Modifier
                        .fillParentMaxWidth()
                        .padding(top = 48.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    } else {
        items(articles) { article ->
            if (article != null) {
                CategoryArticleItem(
                    article = article,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
    when (val appendState = articles.loadState.append) {
        is LoadState.Error -> item {
            Text(
                text = appendState.error.message ?: "Something went wrong",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.error
            )
        }
        LoadState.Loading -> item {
            Box(modifier = Modifier.fillParentMaxWidth()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.Center)
                )
            }
        }
        is LoadState.NotLoading -> {}
    }
}

@Composable
fun CategoryArticleItem(
    modifier: Modifier = Modifier,
    article: Article,
    onArticleClick: (article: Article) -> Unit
) {
    Row(
        modifier = modifier
            .clickable {
                onArticleClick(article)
            }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            article.author?.let { author ->
                Text(
                    text = "By $author", style = MaterialTheme.typography.labelSmall
                )
            }
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        AsyncImage(
            //Most of the articles do not contain an image. Show any temporary image.
            model = article.imageURL ?: TemporaryImageURL,
            contentDescription = "Article Thumbnail",
            modifier = Modifier
                .padding(start = 12.dp)
                .width(78.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun ShimmeringCategoryArticle(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Shimmer(
                modifier = Modifier
                    .height(MaterialTheme.typography.labelSmall.fontSize.toDp())
                    .fillMaxWidth(Random.nextInt(30, 60) / 100f)
            )
            Spacer(modifier = Modifier.padding(top = 8.dp))
            repeat(4) {
                Shimmer(
                    modifier = Modifier
                        .height(MaterialTheme.typography.titleLarge.fontSize.toDp())
                        .fillMaxWidth(Random.nextInt(60, 100) / 100f)
                        .padding(2.dp)
                )
            }
        }
        Shimmer(
            modifier = Modifier
                .padding(start = 12.dp)
                .width(78.dp)
                .aspectRatio(1f)
        )
    }
}

@Composable
fun TextUnit.toDp() = with(LocalDensity.current) {
    this@toDp.toDp()
}