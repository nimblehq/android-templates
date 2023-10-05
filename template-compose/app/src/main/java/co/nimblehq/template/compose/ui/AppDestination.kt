package co.nimblehq.template.compose.ui

import androidx.navigation.NamedNavArgument

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    object Up : AppDestination()

    object RootNavGraph : AppDestination("rootNavGraph")

    object MainNavGraph : AppDestination("mainNavGraph")

    object Home : AppDestination("home")
}
