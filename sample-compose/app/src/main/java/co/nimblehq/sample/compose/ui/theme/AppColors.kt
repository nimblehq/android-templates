package co.nimblehq.sample.compose.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Base colors here
internal val GreenCitrus = Color(0xFF99CC00)

/**
 * Expand the final [ColorScheme] class to provide more custom app colors.
 */
abstract class AppColors(
    var colorScheme: ColorScheme = LightColorPalette,

    // Custom colors here
    val topAppBarBackground: Color = GreenCitrus
) {
    open val themeColors: ColorScheme
        get() = colorScheme
}

internal val LightColorPalette: ColorScheme = lightColorScheme()

internal val DarkColorPalette: ColorScheme = darkColorScheme()

internal object AppColorsImpl : AppColors()

internal val LocalAppColors = staticCompositionLocalOf<AppColors> { AppColorsImpl }
