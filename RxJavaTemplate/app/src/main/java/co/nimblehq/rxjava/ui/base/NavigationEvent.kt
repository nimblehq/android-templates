package co.nimblehq.rxjava.ui.base

import co.nimblehq.rxjava.ui.screens.second.SecondBundle

sealed class NavigationEvent {
    data class Second(val bundle: SecondBundle) : NavigationEvent()
}
