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
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.nimblehq.sample.compose.extensions.setEdgeToEdgeConfig
import co.nimblehq.sample.compose.navigation.EntryProviderInstaller
import co.nimblehq.sample.compose.navigation.Navigator
import co.nimblehq.sample.compose.ui.common.URL_SECOND_SCREEN
import co.nimblehq.sample.compose.ui.screens.main.MainDestination
import co.nimblehq.sample.compose.ui.theme.ComposeTheme
import co.nimblehq.sample.compose.util.DeepLinkMatcher
import co.nimblehq.sample.compose.util.DeepLinkPattern
import co.nimblehq.sample.compose.util.DeepLinkRequest
import co.nimblehq.sample.compose.util.KeyDecoder
import co.nimblehq.sample.compose.util.LocalResultEventBus
import co.nimblehq.sample.compose.util.ResultEventBus
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val TWEEN_DURATION_IN_MILLIS = 500;

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    internal val deepLinkPatterns: List<DeepLinkPattern<out Any>> = listOf(
        DeepLinkPattern(MainDestination.Second.serializer(), URL_SECOND_SCREEN.toUri()),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        handleNewIntent(intent)
        setContent {
            val eventBus = remember { ResultEventBus() }

            ComposeTheme {
                CompositionLocalProvider(LocalResultEventBus.provides(eventBus)) {
                    NavDisplay(
                        backStack = navigator.backStack,
                        onBack = { navigator.goBack() },
                        entryDecorators = listOf(
                            rememberSaveableStateHolderNavEntryDecorator(),
                            rememberViewModelStoreNavEntryDecorator()
                        ),
                        entryProvider = entryProvider {
                            entryProviderScopes.forEach { builder -> this.builder() }
                        },
                        transitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = { it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { -it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
                            )
                        },
                        popTransitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
                            )
                        },
                        predictivePopTransitionSpec = {
                            slideInHorizontally(
                                initialOffsetX = { -it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
                            ) togetherWith slideOutHorizontally(
                                targetOffsetX = { it },
                                animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
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
        val uri: Uri? = intent.data
        val deepLinkNavKey: Any? = uri?.let {
            val request = DeepLinkRequest(uri)
            val match = deepLinkPatterns.firstNotNullOfOrNull { pattern ->
                DeepLinkMatcher(request, pattern).match()
            }
            match?.let {
                KeyDecoder(match.args)
                    .decodeSerializableValue(match.serializer)
            }
        }

        if (deepLinkNavKey != null) {
            navigator.goTo(deepLinkNavKey)
            intent.data = null
        }
    }
}
