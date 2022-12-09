package co.nimblehq.sample.compose.ui

import androidx.navigation.*

const val KEY_ID = "id"

sealed class AppDestination(val route: String = "") {

    open val arguments: List<NamedNavArgument> = emptyList()

    open var destination: String = route

    //====================================================//

    object Up : AppDestination()

    object Home : AppDestination("home")

    /**
     * We can define route as "coin/details" without "coinId" parameter because we're passing it as argument already.
     * So either passing "coinId" via arguments or passing it via route.
     *
     * We keep passing "coinId" in both route and arguments for this destination to give example of navigation concept
     * about how to build a destination with parameters.
     */
    object Second : AppDestination("second/{$KEY_ID}") {

        override val arguments = listOf(
            navArgument(KEY_ID) { type = NavType.StringType }
        )

        fun buildDestination(id: String) = apply {
            destination = "second/$id"
        }
    }
}
