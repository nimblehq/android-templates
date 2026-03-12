package co.nimblehq.template.compose.ui.screens

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import co.nimblehq.template.compose.extensions.setEdgeToEdgeConfig
import co.nimblehq.template.compose.navigation.AppNavigation
import co.nimblehq.template.compose.navigation.EntryProviderInstaller
import co.nimblehq.template.compose.navigation.Navigator
import co.nimblehq.template.compose.ui.theme.ComposeTheme
import co.nimblehq.template.compose.util.DeepLinkMatcher
import co.nimblehq.template.compose.util.DeepLinkPattern
import co.nimblehq.template.compose.util.DeepLinkRequest
import co.nimblehq.template.compose.util.KeyDecoder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.toImmutableSet
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var entryProviderScopes: Set<@JvmSuppressWildcards EntryProviderInstaller>

    /**
     * List of deep link patterns supported by the app.
     *
     * **Template note:** Navigation 3 does not natively support deep links. Add [DeepLinkPattern]
     * instances here for each deep link your app should handle.
     *
     * Example:
     * ```
     * DeepLinkPattern(Home.serializer(), Uri.parse("https://example.com/home"))
     * ```
     */
    internal val deepLinkPatterns: List<DeepLinkPattern<out Any>> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setEdgeToEdgeConfig()
        handleNewIntent(intent)
        setContent {
            ComposeTheme {
                AppNavigation(
                    navigator = navigator,
                    entryProviderScopes = entryProviderScopes.toImmutableSet()
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handleNewIntent(intent)
    }

    private fun handleNewIntent(intent: Intent) {
        val uri = intent.data ?: return
        val deepLinkNavKey = resolveDeepLinkNavKey(uri)
        intent.data = null
        if (deepLinkNavKey != null) navigator.goTo(deepLinkNavKey)
    }

    private fun resolveDeepLinkNavKey(uri: Uri): Any? {
        val request = DeepLinkRequest(uri)
        val match = deepLinkPatterns.firstNotNullOfOrNull { pattern ->
            DeepLinkMatcher(request, pattern).match()
        } ?: return null
        return try {
            KeyDecoder(match.args).decodeSerializableValue(match.serializer)
        } catch (_: Exception) { null }
    }
}
