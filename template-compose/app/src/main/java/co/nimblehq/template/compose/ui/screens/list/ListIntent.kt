package co.nimblehq.template.compose.ui.screens.list

import co.nimblehq.template.compose.common.BaseIntent

sealed class ListIntent : BaseIntent() {
    data object LoadModels : ListIntent()
    data object NavigateBack : ListIntent()
}
