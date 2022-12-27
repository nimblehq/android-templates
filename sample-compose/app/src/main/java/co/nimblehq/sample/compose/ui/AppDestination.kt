package co.nimblehq.sample.compose.ui

import androidx.navigation.*

const val KEY_ID = "id"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    object Up : AppDestination()

    object Home : AppDestination("home")

    object Second : AppDestination("second/{$KEY_ID}") {

        override val arguments = listOf(
            navArgument(KEY_ID) { type = NavType.StringType }
        )

        fun buildDestination(id: String) = apply {
            destination = "second/$id"
        }
    }
}
