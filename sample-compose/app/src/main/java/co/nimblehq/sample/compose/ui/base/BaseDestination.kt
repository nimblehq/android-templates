package co.nimblehq.sample.compose.ui.base

import androidx.navigation.NamedNavArgument

abstract class BaseDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    object Up : BaseDestination()
}
