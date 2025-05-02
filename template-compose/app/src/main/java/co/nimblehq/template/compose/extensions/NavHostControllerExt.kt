package co.nimblehq.template.compose.extensions

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import co.nimblehq.template.compose.ui.base.BaseAppDestination
import kotlin.collections.component1
import kotlin.collections.component2

/**
 * Use this extension or [navigate(BaseAppDestination.Up())] to prevent duplicated navigation events
 */
fun NavHostController.navigateAppDestinationUp() {
    navigateTo(BaseAppDestination.Up())
}

private const val IntervalInMillis: Long = 1000L
private var lastNavigationEventExecutedTimeInMillis: Long = 0L

/**
 * Use this extension to prevent duplicated navigation events with the same destination in a short time
 */
private fun NavHostController.throttleNavigation(
    appDestination: BaseAppDestination,
    onNavigate: () -> Unit,
) {
    val currentTime = System.currentTimeMillis()
    if (currentBackStackEntry?.destination?.route == appDestination.route
        && (currentTime - lastNavigationEventExecutedTimeInMillis < IntervalInMillis)
    ) {
        return
    }
    lastNavigationEventExecutedTimeInMillis = currentTime

    onNavigate()
}

/**
 * Navigate to provided [BaseAppDestination]
 * Caution to use this method. This method use savedStateHandle to store the Parcelable data.
 * When previousBackstackEntry is popped out from navigation stack, savedStateHandle will return null and cannot retrieve data.
 * eg.Login -> Home, the Login screen will be popped from the back-stack on logging in successfully.
 */
fun <T : BaseAppDestination> NavHostController.navigateTo(
    appDestination: T,
    builder: (NavOptionsBuilder.() -> Unit)? = null,
) = throttleNavigation(appDestination) {
    when (appDestination) {
        is BaseAppDestination.Up -> {
            appDestination.results.forEach { (key, value) ->
                previousBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigateUp()
        }
        else -> {
            appDestination.parcelableArgument?.let { (key, value) ->
                currentBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigate(route = appDestination.destination) {
                if (builder != null) {
                    builder()
                }
            }
        }
    }
}
