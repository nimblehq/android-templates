package co.nimblehq.sample.compose.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.sample.compose.ui.AppNavGraph
import co.nimblehq.sample.compose.ui.navigation.Navigator
import co.nimblehq.sample.compose.ui.navigation.rememberNavigationState
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                val navigationState = rememberNavigationState<MainDestination>(MainDestination.Home)
                val navigator = Navigator(navigationState)
                AppNavGraph(
                    navigationState = navigationState,
                    navigator = navigator
                )
            }
        }
    }
}
