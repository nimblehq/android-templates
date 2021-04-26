package co.nimblehq.coroutine.ui.base

sealed class NavigationException(
    cause: Throwable?
) : Throwable(cause) {

    class UnsupportedNavigationException(
        currentGraph: String?,
        currentDestination: String?
    ) : NavigationException(RuntimeException("Unsupported navigation on $currentGraph at $currentDestination"))
}
