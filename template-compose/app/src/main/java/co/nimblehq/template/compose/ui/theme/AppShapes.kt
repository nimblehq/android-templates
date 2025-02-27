package co.nimblehq.template.compose.ui.theme

import androidx.compose.material3.Shapes
import androidx.compose.runtime.staticCompositionLocalOf

interface AppShapes {
    val themeShapes: Shapes
        get() = Shapes(
            // Custom shapes here
        )
}

object AppShapesImpl : AppShapes

internal val LocalAppShapes = staticCompositionLocalOf<AppShapes> { AppShapesImpl }
