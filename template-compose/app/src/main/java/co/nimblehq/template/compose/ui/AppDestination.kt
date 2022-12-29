package co.nimblehq.template.compose.ui

import androidx.navigation.*

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    object Up : AppDestination()

    object Home : AppDestination("home")
}
