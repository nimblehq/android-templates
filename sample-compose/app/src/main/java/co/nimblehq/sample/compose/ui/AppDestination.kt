package co.nimblehq.sample.compose.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import co.nimblehq.sample.compose.model.UiModel

const val KeyId = "id"
const val KeyModel = "model"
const val KeyResultOk = "keyResultOk"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    open var parcelableArgument: Pair<String, Any?> = "" to null

    data class Up(val results: List<Result> = emptyList()) : AppDestination()

    object RootNavGraph : AppDestination("rootNavGraph")

    object MainNavGraph : AppDestination("mainNavGraph")

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

data class Result(
    val key: String,
    val value: Any,
)
