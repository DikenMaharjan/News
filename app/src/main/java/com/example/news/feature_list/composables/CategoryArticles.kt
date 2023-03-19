package com.example.news.feature_list.composables


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.utils.image.TemporaryImageURL

fun LazyListScope.categoryArticles(
    articles: LazyPagingItems<Article>
) {
    items(articles) { article ->
        if (article != null) {
            CategoryArticleItem(article = article)
        }
    }
}

@Composable
fun CategoryArticleItem(
    modifier: Modifier = Modifier,
    article: Article
) {
    Row(
        modifier = modifier.padding(12.dp),
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