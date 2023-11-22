package co.nimblehq.template.compose.ui.base

import androidx.navigation.NamedNavArgument

abstract class BaseDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    object Up : BaseDestination()
}
