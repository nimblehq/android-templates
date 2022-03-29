package co.nimblehq.rxjava.ui.base

import co.nimblehq.rxjava.domain.data.error.AppError

sealed class NavigationError(
    cause: Throwable?
) : AppError(cause) {

    class UnsupportedNavigationError(
        currentGraph: String?,
        currentDestination: String?
    ) : NavigationError(RuntimeException("Unsupported navigation on $currentGraph at $currentDestination"))
}
