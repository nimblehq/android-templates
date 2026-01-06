package co.nimblehq.sample.compose.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.nimblehq.sample.compose.extensions.setEdgeToEdgeConfig
import co.nimblehq.sample.compose.navigation.EntryProviderInstaller
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.ui.common.URL_SEARCH
import co.nimblehq.sample.compose.ui.screens.details.DetailsScreen
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.util.DeepLinkMatcher
import co.nimblehq.sample.compose.util.DeepLinkPattern
import co.nimblehq.sample.compose.util.DeepLinkRequest
import co.nimblehq.sample.compose.util.KeyDecoder
import co.nimblehq.sample.compose.util.LocalResultEventBus
import co.nimblehq.sample.compose.util.ResultEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    /** STEP 1. Parse supported deeplinks */
    // internal so that landing activity can link to this in the kdocs
    internal val deepLinkPatterns: List<DeepLinkPattern<out NavKey>> = listOf(
        // "https://www.android.nimblehq.co/users/search?username={username}"
        DeepLinkPattern(DetailsScreen.Search.serializer(), (URL_SEARCH).toUri()),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        setContent {
            val eventBus = remember { ResultEventBus() }

            handleNewIntent(intent)

            ComposeTheme {
                CompositionLocalProvider(LocalResultEventBus.provides(eventBus)) {
                    NavDisplay(
                        backStack = navigator.backStack,
                        onBack = { navigator.goBack() },
                        // In order to add the `ViewModelStoreNavEntryDecorator` (see comment below for why)
                        // we also need to add the default `NavEntryDecorator`s as well. These provide
                        // extra information to the entry's content to enable it to display correctly
                        // and save its state.
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            entryProviderScopes.forEach { builder -> this.builder() }
                        },
                        transitionSpec = {
                            // Slide in from right when navigating forward
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(500)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(500)
                            )
                        },
                        popTransitionSpec = {
                            // Slide in from left when navigating back
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(500)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(500)
                            )
                        },
                        predictivePopTransitionSpec = {
                            // Slide in from left when navigating back
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(500)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(500)
                            )
                        }
                    )
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNewIntent(intent)
    }

    private fun handleNewIntent(intent: Intent) {
        // retrieve the target Uri
        val uri: Uri? = intent.data
        // associate the target with the correct backstack key
        val deepLinkNavKey: NavKey? = uri?.let {
            /** STEP 2. Parse requested deeplink */
            val request = DeepLinkRequest(uri)
            /** STEP 3. Compared requested with supported deeplink to find match*/
            val match = deepLinkPatterns.firstNotNullOfOrNull { pattern ->
                DeepLinkMatcher(request, pattern).match()
            }
            /** STEP 4. If match is found, associate match to the correct key*/
            match?.let {
                //leverage kotlinx.serialization's Decoder to decode
                // match result into a backstack key
                KeyDecoder(match.args)
                    .decodeSerializableValue(match.serializer)
            }
        }

        if (deepLinkNavKey != null) navigator.goTo(deepLinkNavKey)
    }
}
