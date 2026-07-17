package co.nimblehq.template.compose.navigation.entry

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import co.nimblehq.template.compose.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable


@Serializable
data object HomeDestination : NavKey

fun EntryProviderScope<Any>.homeDestinationEntry(onNavigate: (NavKey) -> Unit) {
    entry<HomeDestination> {
        HomeScreen(viewModel = hiltViewModel(), onNavigate = onNavigate)
    }
}
