@file:Suppress("TopLevelPropertyNaming")

package co.nimblehq.coroutine.ui.screens.compose.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

object Palette {
    val ComposeLightPalette = lightColors(
        primary = Color.BlueFreeSpeech,
        background = Color.AlmostWhite
    )
}

@Composable
fun ComposeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = Palette.ComposeLightPalette,
        typography = Typography.ComposeTypography,
        shapes = Shape.ComposeShapes,
        content = content
    )
}
