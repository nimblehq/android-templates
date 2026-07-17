package co.nimblehq.template.compose.ui.screens.home

import co.nimblehq.template.compose.common.BaseIntent

sealed class HomeIntent : BaseIntent() {
    data object NavigateToList : HomeIntent()
}
