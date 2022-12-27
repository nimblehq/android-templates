package co.nimblehq.sample.compose.ui

import androidx.navigation.*

const val KeyId = "id"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    object Up : AppDestination()

    object Home : AppDestination("home")

    object Second : AppDestination("second/{$KeyId}") {

        override val arguments = listOf(
            navArgument(KeyId) { type = NavType.StringType }
        )

        fun buildDestination(id: String) = apply {
            destination = "second/$id"
        }
    }
}
