package co.nimblehq.coroutine.ui.base

import co.nimblehq.coroutine.ui.screens.second.SecondBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
}
