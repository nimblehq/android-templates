package co.nimblehq.sample.compose.ui.screens.main.third

import co.nimblehq.sample.compose.ui.base.ViewEffect
import co.nimblehq.sample.compose.ui.base.ViewIntent
import co.nimblehq.sample.compose.ui.base.ViewState

data object ThirdViewState : ViewState

sealed interface ThirdViewIntent : ViewIntent

sealed interface ThirdViewEffect : ViewEffect
