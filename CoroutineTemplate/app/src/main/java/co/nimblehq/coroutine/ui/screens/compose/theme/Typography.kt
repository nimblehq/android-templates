@file:Suppress("TopLevelPropertyNaming")

package co.nimblehq.coroutine.ui.screens.compose.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import co.nimblehq.coroutine.R

private val Montserrat = FontFamily(
    Font(R.font.montserrat_regular)
)

val Typography = Typography(
    defaultFontFamily = Montserrat
)
