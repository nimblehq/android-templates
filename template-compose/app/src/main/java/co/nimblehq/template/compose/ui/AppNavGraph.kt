package co.nimblehq.template.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import co.nimblehq.template.compose.ui.screens.main.mainNavGraph

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
    destination: AppDestination,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = destination.deepLinks,
        content = content
    )
}

fun NavHostController.navigate(appDestination: AppDestination) {
    when (appDestination) {
        is AppDestination.Up -> {
            appDestination.results.forEach { (key, value) ->
                previousBackStackEntry?.savedStateHandle?.set(key, value)
            }
            navigateUp()
        }
        else -> navigate(route = appDestination.destination)
    }
}
