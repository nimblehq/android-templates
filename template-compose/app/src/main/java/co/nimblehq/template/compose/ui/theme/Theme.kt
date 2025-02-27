package co.nimblehq.template.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*

@Composable
fun ComposeTheme(
    colors: AppColors = LocalAppColors.current,
    dimensions: AppDimensions = LocalAppDimensions.current,
    shapes: AppShapes = LocalAppShapes.current,
    styles: AppStyles = LocalAppStyles.current,
    typography: AppTypography = LocalAppTypography.current,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }
    colors.run {
        this.colorScheme = colorScheme
    }

    CompositionLocalProvider(
        LocalAppColors provides colors,
        LocalAppDimensions provides dimensions,
        LocalAppShapes provides shapes,
        LocalAppTypography provides typography,
        LocalAppStyles provides styles,
    ) {
        MaterialTheme(
            colorScheme = colors.themeColors,
            typography = typography.themeTypography,
            shapes = shapes.themeShapes,
            content = content
        )
    }
}

/**
 * Alternate to [MaterialTheme] allowing us to add our own theme systems
 * or to extend [MaterialTheme]'s types e.g. return our own [AppColors] extension.
 */
object AppTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shapes: AppShapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShapes.current

    val dimensions: AppDimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalAppDimensions.current

    val styles: AppStyles
        @Composable
        @ReadOnlyComposable
        get() = LocalAppStyles.current
}
