package com.example.news.feature_list.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.ui.theme.DarkColors


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
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            1f to Color.Black
                        )
                    )
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

@Composable
fun StackLayout(
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


