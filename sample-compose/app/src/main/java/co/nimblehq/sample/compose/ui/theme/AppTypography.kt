package co.nimblehq.sample.compose.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf

interface AppTypography {
    // Custom typography here
    val themeTypography: Typography
        get() = Typography()
}

object AppTypographyImpl : AppTypography

internal val LocalAppTypography = staticCompositionLocalOf<AppTypography> { AppTypographyImpl }
