package co.nimblehq.sample.compose.ui.base

import co.nimblehq.sample.compose.model.UiModel

sealed class NavigationEvent {
    data class Second(val uiModel: UiModel) : NavigationEvent()
}
