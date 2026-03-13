@file:Suppress("MatchingDeclarationName")

package co.nimblehq.template.compose.navigation.entries

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import co.nimblehq.template.compose.navigation.Navigator
import co.nimblehq.template.compose.ui.screens.main.home.HomeScreen

/**
 * **Template note:** Annotate this NavKey with `@Serializable` if you need to reference
 * its serializer in a [DeepLinkPattern] (e.g. `DeepLinkPattern(Home.serializer(), uri)`).
 */
data object Home : NavKey

fun EntryProviderScope<Any>.homeScreenEntry(navigator: Navigator) {
    entry<Home> {
        HomeScreen(viewModel = hiltViewModel(), navigator = navigator)
    }
}
