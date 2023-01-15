package co.nimblehq.template.compose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.staticCompositionLocalOf

private val Typography = Typography(
    // Custom typography here
)

internal val LocalAppTypography = staticCompositionLocalOf { Typography }
