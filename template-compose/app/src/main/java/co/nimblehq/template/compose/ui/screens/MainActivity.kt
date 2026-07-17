package co.nimblehq.template.compose.ui.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.template.compose.extensions.setEdgeToEdgeConfig
import co.nimblehq.template.compose.navigation.AppNavigation
import co.nimblehq.template.compose.navigation.navigator.AppNavigator
import co.nimblehq.template.compose.navigation.navigator.EntryProviderInstaller
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableSet
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        setContent {
            ComposeTheme {
                AppNavigation(
                    navigator = navigator,
                    entryProviderScopes = entryProviderScopes.toImmutableSet()
                )
            }
        }
    }
}
