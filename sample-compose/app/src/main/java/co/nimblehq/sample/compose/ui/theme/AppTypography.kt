package co.nimblehq.sample.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf

private val Typography = Typography(
    // Custom TextStyle here
)

internal val LocalAppTypography = staticCompositionLocalOf { Typography }
