package co.nimblehq.template.compose.ui.screens.list

import co.nimblehq.template.compose.common.BaseIntent
import co.nimblehq.template.compose.ui.screens.home.HomeIntent

sealed class ListIntent : BaseIntent() {
    data object LoadModels : ListIntent()
    data object NavigateBack: ListIntent()
}
