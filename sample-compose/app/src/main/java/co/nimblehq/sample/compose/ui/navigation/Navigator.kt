package co.nimblehq.sample.compose.ui.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Navigator class to handle navigation actions.
 */
class Navigator<T : NavKey>(
    private val navigationState: NavigationState<T>,
) {
    /**
     * Navigate to a destination.
     */
    fun navigate(destination: T) {
        navigationState.backStack.add(destination)
    }

    /**
     * Navigate back. Returns true if navigation was successful.
     */
    fun goBack(): Boolean {
        return if (navigationState.backStack.size > 1) {
            navigationState.backStack.removeLast()
            true
        } else {
            false
        }
    }

    /**
     * Pop back stack up to the specified destination (inclusive or exclusive).
     */
    fun popBackTo(destination: T, inclusive: Boolean = false) {
        val index = navigationState.backStack.lastIndexOf(destination)
        if (index != -1) {
            val removeCount = navigationState.backStack.size - index - if (inclusive) 0 else 1
            repeat(removeCount) {
                if (navigationState.backStack.size > 1) {
                    navigationState.backStack.removeLast()
                }
            }
        }
    }
}
