package co.nimblehq.template.compose.navigation.entry

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import co.nimblehq.template.compose.ui.screens.list.ListScreen
import kotlinx.serialization.Serializable

@Serializable
data object ListDestination : NavKey

fun EntryProviderScope<Any>.listDestinationEntry(onNavigateBack: () -> Unit) {
    entry<ListDestination> {
        ListScreen(viewModel = hiltViewModel(), onNavigateBack = onNavigateBack)
    }
}
