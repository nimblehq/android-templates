package co.nimblehq.template.compose.ui.screens.home

import co.nimblehq.template.compose.common.BaseViewModel
import co.nimblehq.template.compose.navigation.entry.ListDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeIntent, Unit>() {

    override fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.NavigateToList -> emitNavigation(ListDestination)
        }
    }
}
