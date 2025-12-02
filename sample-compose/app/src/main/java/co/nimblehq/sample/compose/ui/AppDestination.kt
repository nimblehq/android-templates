package co.nimblehq.sample.compose.ui

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface AppDestination : NavKey {

    @Serializable
    data object MainNavGraph : AppDestination
}
