package co.nimblehq.template.compose.ui.base

import androidx.navigation.NamedNavArgument

abstract class BaseDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open val deepLinks: List<String> = emptyList()

    open var destination: String = route

    data class Up(val results: HashMap<String, Any> = hashMapOf()) : BaseDestination() {

        fun addResult(key: String, value: Any) = apply {
            results[key] = value
        }
    }
}
