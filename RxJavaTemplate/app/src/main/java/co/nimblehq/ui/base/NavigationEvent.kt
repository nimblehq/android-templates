package co.nimblehq.ui.base

import co.nimblehq.ui.screens.second.SecondBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
}
