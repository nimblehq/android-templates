package co.nimblehq.template.compose.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import co.nimblehq.template.compose.navigation.navigator.AppNavigator
import co.nimblehq.template.compose.navigation.navigator.EntryProviderInstaller
import kotlinx.collections.immutable.ImmutableSet

private const val TWEEN_DURATION_IN_MILLIS = 500

@Composable
fun AppNavigation(
    navigator: AppNavigator,
    entryProviderScopes: ImmutableSet<EntryProviderInstaller>,
) {
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
        predictivePopTransitionSpec = { horizontalSlideTransition(isPop = true) },
        modifier = Modifier.windowInsetsPadding(WindowInsets.safeDrawing)
    )
}

private fun horizontalSlideTransition(isPop: Boolean): ContentTransform =
    slideInHorizontally(
        initialOffsetX = { if (isPop) -it else it },
        animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
    ) togetherWith slideOutHorizontally(
        targetOffsetX = { if (isPop) it else -it },
        animationSpec = tween(TWEEN_DURATION_IN_MILLIS)
    )

