package co.nimblehq.sample.compose.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavKey

/**
 * Remember a [NavigationState].
 * Note: Navigation3's NavDisplay handles state restoration internally,
 * so we don't need to use rememberSaveable here.
 */
@Composable
fun <T : NavKey> rememberNavigationState(
    startDestination: T,
): NavigationState<T> = remember {
    NavigationState(startDestination)
}

/**
 * Holds the navigation state including the back stack.
 */
class NavigationState<T : NavKey>(
    startDestination: T,
) {
    val backStack = mutableStateListOf(startDestination)

    val currentDestination: T
        get() = backStack.last()
}
