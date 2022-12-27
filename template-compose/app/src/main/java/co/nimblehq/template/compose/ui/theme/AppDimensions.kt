package co.nimblehq.template.compose.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class AppDimensions {
    // Custom colors here
    val spacingNormal = 16.dp
}

internal val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }
