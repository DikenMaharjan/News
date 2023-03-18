package com.example.news.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.news.R

val fontFamily = FontFamily(
    Font(R.font.open_sans)
)

// Set of Material typography styles to start with
val Typography = Typography().run {
    Typography(
        displayLarge = this.displayLarge.copy(
            fontFamily = fontFamily
        ),
        displayMedium = this.displayMedium.copy(
            fontFamily = fontFamily
        ),
        displaySmall = this.displaySmall.copy(
            fontFamily = fontFamily
        ),
        headlineLarge = this.headlineLarge.copy(
            fontFamily = fontFamily
        ),
        headlineMedium = this.headlineMedium.copy(
            fontFamily = fontFamily
        ),
        headlineSmall = this.headlineSmall.copy(
            fontFamily = fontFamily
        ),
        labelLarge = this.labelLarge.copy(
            fontFamily = fontFamily
        ),
        labelSmall = this.labelSmall.copy(
            fontFamily = fontFamily
        ),
        labelMedium = this.labelMedium.copy(
            fontFamily = fontFamily
        ),
        bodyLarge = this.bodyLarge.copy(
            fontFamily = fontFamily
        ),
        bodyMedium = this.bodyMedium.copy(
            fontFamily = fontFamily
        ),
        bodySmall = this.bodySmall.copy(
            fontFamily = fontFamily
        ),
        titleLarge = this.titleLarge.copy(
            fontFamily = fontFamily
        ),
        titleSmall = this.titleSmall.copy(
            fontFamily = fontFamily
        ),
        titleMedium = this.titleMedium.copy(
            fontFamily = fontFamily
        )
    )
}