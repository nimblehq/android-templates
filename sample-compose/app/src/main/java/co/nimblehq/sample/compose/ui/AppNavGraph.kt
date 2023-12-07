package co.nimblehq.sample.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import co.nimblehq.sample.compose.ui.base.BaseDestination
import co.nimblehq.sample.compose.ui.screens.main.mainNavGraph

@Composable
fun AppNavGraph(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        route = AppDestination.RootNavGraph.route,
        startDestination = AppDestination.MainNavGraph.destination
    ) {
        mainNavGraph(navController = navController)
    }
}

fun NavGraphBuilder.composable(
    destination: BaseDestination,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks.map {
            navDeepLink {
                uriPattern = it
            }
        },
        content = content
    )
}

/**
 * Navigate to provided [BaseDestination] with a Pair of key value String and Data [parcel]
 * Caution to use this method. This method use savedStateHandle to store the Parcelable data.
 * When previousBackstackEntry is popped out from navigation stack, savedStateHandle will return null and cannot retrieve data.
 * eg.Login -> Home, the Login screen will be popped from the back-stack on logging in successfully.
 */
fun NavHostController.navigate(destination: BaseDestination, parcel: Pair<String, Any?>? = null) {
    when (destination) {
        is BaseDestination.Up -> {
            destination.results.forEach { (key, value) ->
                previousBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigateUp()
        }
        else -> {
            parcel?.let { (key, value) ->
                currentBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigate(route = destination.destination)
        }
    }
}
