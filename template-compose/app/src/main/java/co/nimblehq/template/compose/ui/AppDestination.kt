package co.nimblehq.template.compose.ui

import androidx.navigation.NamedNavArgument

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    data class Up(val results: HashMap<String, Any> = hashMapOf()) : AppDestination() {

        fun addResult(key: String, value: Any) = apply {
            results[key] = value
        }
    }

    object RootNavGraph : AppDestination("rootNavGraph")

    object MainNavGraph : AppDestination("mainNavGraph")

    object Home : AppDestination("home")
}
