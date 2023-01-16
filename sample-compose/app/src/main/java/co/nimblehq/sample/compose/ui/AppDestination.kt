package co.nimblehq.sample.compose.ui

import androidx.navigation.*
import co.nimblehq.sample.compose.model.UiModel

const val KeyId = "id"
const val ModelKey = "model"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcel : Pair<String,UiModel?> = "" to null

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

    object Third: AppDestination("third") {
        fun addParcel(value: UiModel) = apply {
            parcel = ModelKey to value
        }
    }
}
