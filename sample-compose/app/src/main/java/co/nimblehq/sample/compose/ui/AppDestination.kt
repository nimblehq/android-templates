package co.nimblehq.sample.compose.ui

import androidx.navigation.*
import co.nimblehq.sample.compose.model.UiModel

const val KeyId = "id"
const val KeyModel = "model"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    object Up : AppDestination()

    object Home : AppDestination("home")

    object Second : AppDestination("second/{$KeyId}") {

        override val arguments = listOf(
            navArgument(KeyId) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "second/$id"
        }
    }

    object Third : AppDestination("third") {
        fun addParcel(value: UiModel) = apply {
            parcelableArgument = KeyModel to value
        }
    }
}
