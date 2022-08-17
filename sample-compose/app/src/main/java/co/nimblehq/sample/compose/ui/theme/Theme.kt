package co.nimblehq.sample.compose.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

@Suppress("FunctionNaming")
@Composable
fun Theme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = lightColors(
            primary = Color.GreenCitrus,
            primaryVariant = Color.GreenChristi,
            secondary = Color.GreenCitrus
        ),
        content = content
    )
}
