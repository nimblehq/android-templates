package co.nimblehq.sample.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.nimblehq.sample.compose.ui.navigation.Navigator
import co.nimblehq.sample.compose.ui.navigation.NavigationState
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.screens.main.home.HomeScreen
import co.nimblehq.sample.compose.ui.screens.main.second.SecondScreen
import co.nimblehq.sample.compose.ui.screens.main.third.ThirdScreen

@Composable
fun AppNavGraph(
    navigationState: NavigationState<MainDestination>,
    navigator: Navigator<MainDestination>,
) {
    NavDisplay(
        backStack = navigationState.backStack,
        onBack = { navigator.goBack() },
        entryDecorators = listOf(rememberSaveableStateHolderNavEntryDecorator()),
        entryProvider = { key ->
            when (key) {
                is MainDestination.Home -> NavEntry(key) {
                    HomeScreen(
                        navigator = { destination -> navigator.navigate(destination as MainDestination) }
                    )
                }
                is MainDestination.Second -> NavEntry(key) {
                    SecondScreen(
                        navigator = { navigator.goBack() },
                        id = key.id
                    )
                }
                is MainDestination.Third -> NavEntry(key) {
                    ThirdScreen(
                        navigator = {},
                        model = key.model
                    )
                }
            }
        }
    )
}
