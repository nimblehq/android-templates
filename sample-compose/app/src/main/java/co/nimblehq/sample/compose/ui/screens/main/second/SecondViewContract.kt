package co.nimblehq.sample.compose.ui.screens.main.second

import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.base.ViewEffect
import co.nimblehq.sample.compose.ui.base.ViewIntent
import co.nimblehq.sample.compose.ui.base.ViewState

data object SecondViewState : ViewState

sealed interface SecondViewIntent : ViewIntent {

    data object UpdateClick : SecondViewIntent
}

sealed interface SecondViewEffect : ViewEffect {

    data class Navigate(val destination: BaseDestination) : SecondViewEffect
}
