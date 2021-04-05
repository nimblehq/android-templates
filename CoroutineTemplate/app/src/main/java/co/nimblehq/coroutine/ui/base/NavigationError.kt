package co.nimblehq.coroutine.ui.base

sealed class NavigationError(
    cause: Throwable?
) : Throwable(cause) {

    class UnsupportedNavigationError(
        currentGraph: String?,
        currentDestination: String?
    ) : NavigationError(RuntimeException("Unsupported navigation on $currentGraph at $currentDestination"))
}
