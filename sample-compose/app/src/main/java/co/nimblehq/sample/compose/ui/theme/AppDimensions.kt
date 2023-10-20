package co.nimblehq.sample.compose.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class AppDimensions {
    // Custom dimensions here
    val spacing2XSmall = 4.dp
    val spacingXSmall = 8.dp
    val spacingSmall = 12.dp
    val spacingMedium = 16.dp
    val spacingLarge = 20.dp
    val spacingXLarge = 24.dp
    val spacing2XLarge = 28.dp
    val spacing3XLarge = 32.dp
}

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
