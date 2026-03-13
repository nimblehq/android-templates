package co.nimblehq.template.compose.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.nimblehq.template.compose.util.LocalResultEventBus
import co.nimblehq.template.compose.util.ResultEventBus
import kotlinx.collections.immutable.ImmutableSet

private const val TWEEN_DURATION_IN_MILLIS = 500

@Composable
fun AppNavigation(
    navigator: Navigator,
    entryProviderScopes: ImmutableSet<EntryProviderInstaller>,
) {
    val eventBus = remember { ResultEventBus() }
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
            transitionSpec = { horizontalSlideTransition(isPop = false) },
            popTransitionSpec = { horizontalSlideTransition(isPop = true) },
            predictivePopTransitionSpec = { horizontalSlideTransition(isPop = true) }
        )
    }
}

private fun horizontalSlideTransition(isPop: Boolean): ContentTransform =
    slideInHorizontally(
        initialOffsetX = { if (isPop) -it else it },
        animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { if (isPop) it else -it },
        animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
    )

