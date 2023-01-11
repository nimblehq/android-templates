package co.nimblehq.sample.compose.ui.screens.home

import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.model.UiModel

data class HomeViewState(
    val uiModels: List<UiModel> = emptyList(),
    val firstTimeLaunch: Boolean = false,
    val showLoading: IsLoading = false
)
