package co.nimblehq.sample.compose.ui.screens.main.home

import co.nimblehq.sample.compose.lib.IsLoading
import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.base.ViewEffect
import co.nimblehq.sample.compose.ui.base.ViewIntent
import co.nimblehq.sample.compose.ui.base.ViewState
import co.nimblehq.sample.compose.ui.models.UiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeViewState(
    val isLoading: IsLoading = false,
    val uiModels: ImmutableList<UiModel> = persistentListOf(),
) : ViewState

sealed interface HomeViewIntent : ViewIntent {

    data class ItemClick(val uiModel: UiModel) : HomeViewIntent

    data class ItemLongClick(val uiModel: UiModel) : HomeViewIntent
}

sealed interface HomeViewEffect : ViewEffect {

    data class Navigate(val destination: BaseDestination) : HomeViewEffect

    data class ShowError(val error: Throwable) : HomeViewEffect

    data object ShowFirstTimeLaunchMessage : HomeViewEffect
}
