@file:Suppress("TopLevelPropertyNaming")

package co.nimblehq.coroutine.ui.screens.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val LightThemeColors = lightColors(
    primary = BlueFreeSpeech,
    background = AlmostWhite
)

@Composable
fun ComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightThemeColors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
