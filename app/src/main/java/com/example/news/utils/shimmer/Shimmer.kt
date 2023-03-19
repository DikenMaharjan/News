package com.example.news.utils.shimmer

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import kotlin.random.Random

@Composable
fun Shimmer(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.shimmer()
    )

}

private fun Modifier.shimmer() = composed {
    val progress by rememberInfiniteTransition().animateFloat(
        initialValue = 0f, targetValue = 1f, animationSpec = infiniteRepeatable(
            animation = tween(1500, delayMillis = Random.nextInt(2000))
        )
    )
    val brush = Brush.linearGradient(
        0f to MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
        progress to MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.1f),
        1f to MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
    )
    clip(
        MaterialTheme.shapes.extraSmall
    ).background(
        brush = brush
    )
}