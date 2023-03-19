package com.example.news.feature_detail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.news.data.models.domain.Article
import com.example.news.utils.date.getUploadedAgoText
import com.example.news.utils.date.toOffsetDateTime
import com.example.news.utils.image.TemporaryImageURL

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    popBackStack: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = popBackStack) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Icon")
                }
            },
            title = {}
        )
        val article = viewModel.article.collectAsState().value
        if (article != null) {
            ArticleDetail(
                modifier = modifier
                    .weight(1f)
                    .padding(24.dp),
                article = article
            )
        }
    }
}

@Composable
fun ArticleDetail(modifier: Modifier = Modifier, article: Article) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = article.title, style = MaterialTheme.typography.headlineSmall)

        AsyncImage(
            model = article.imageURL ?: TemporaryImageURL,
            contentDescription = "Article Thumbnail",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clip(MaterialTheme.shapes.extraSmall),
            contentScale = ContentScale.FillWidth
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            article.author?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Light,
                    modifier = Modifier.align(Alignment.CenterStart)
                )
            }
            Text(
                text = article.publishedAt.toOffsetDateTime().getUploadedAgoText(),
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Light,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
        Divider(modifier = Modifier.padding(top = 8.dp))

        article.content?.let { content ->
            Text(
                text = content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(vertical = 24.dp)
            )
        }
        TextButton(
            onClick = {
                context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(article.url)))
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Click to view full article")
        }
    }
}
