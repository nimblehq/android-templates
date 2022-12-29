package co.nimblehq.sample.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.sample.compose.ui.screens.home.HomeScreen
import co.nimblehq.sample.compose.ui.screens.second.SecondScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Home.destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(AppDestination.Home) {
            HomeScreen(
                navigator = { destination -> navController.navigate(destination) }
            )
        }

        composable(AppDestination.Second) { backStackEntry ->
            SecondScreen(
                navigator = { destination -> navController.navigate(destination) },
                id = backStackEntry.arguments?.getString(KeyId).orEmpty()
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    destination: AppDestination,
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        content = content
    )
}

private fun NavHostController.navigate(appDestination: AppDestination) {
    when (appDestination) {
        is AppDestination.Up -> navigateUp()
        else -> navigate(route = appDestination.destination)
    }
}
