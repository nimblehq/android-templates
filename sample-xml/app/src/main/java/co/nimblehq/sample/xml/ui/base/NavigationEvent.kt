package co.nimblehq.sample.xml.ui.base

import co.nimblehq.sample.xml.model.UiModel

sealed class NavigationEvent {
    data class Second(val uiModel: UiModel) : NavigationEvent()
}
