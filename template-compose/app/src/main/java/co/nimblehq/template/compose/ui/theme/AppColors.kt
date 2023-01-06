package co.nimblehq.template.compose.ui.theme

import androidx.compose.material.*
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * Extend final [Colors] class to provide more custom app colors.
 */
data class AppColors(
    val themeColors: Colors

    // Custom colors here
)

internal val LightColorPalette = AppColors(
    themeColors = lightColors()
)

internal val DarkColorPalette = AppColors(
    themeColors = darkColors()
)

internal val LocalColors = staticCompositionLocalOf { LightColorPalette }
