package co.nimblehq.template.compose.common

import androidx.navigation3.runtime.NavKey

abstract class BaseEvent

data class ErrorEvent(val error: Throwable) : BaseEvent()

data class NavigationEvent(val destination: NavKey) : BaseEvent()
