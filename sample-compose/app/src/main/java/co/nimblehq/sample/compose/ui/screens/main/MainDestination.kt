package co.nimblehq.sample.compose.ui.screens.main

import co.nimblehq.sample.compose.ui.models.UiModel
import kotlinx.serialization.Serializable

sealed interface MainDestination {

    @Serializable
    data object Home : MainDestination

    @Serializable
    data class Second(val id: String) : MainDestination

    @Serializable
    data class Third(val model: UiModel) : MainDestination
}
