package co.nimblehq.sample.compose.ui.screens.main

import androidx.navigation.NavType
import androidx.navigation.navArgument
import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.models.UiModel

const val KeyId = "id"
const val KeyModel = "model"

sealed class MainDestination {

    object Home : BaseDestination("home")

    object Second : BaseDestination("second/{$KeyId}") {

        override val arguments = listOf(
            navArgument(KeyId) { type = NavType.StringType }
        )

        fun createRoute(id: String) = apply {
            destination = "second/$id"
        }
    }

    object Third : BaseDestination("third") {
        fun addParcel(value: UiModel) = apply {
            parcelableArgument = KeyModel to value
        }
    }
}
