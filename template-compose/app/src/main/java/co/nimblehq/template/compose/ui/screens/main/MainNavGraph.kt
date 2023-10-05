package co.nimblehq.template.compose.ui.screens.main

import androidx.navigation.*
import co.nimblehq.template.compose.ui.*
import co.nimblehq.template.compose.ui.screens.main.home.HomeScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = AppDestination.Home.destination
    ) {
        composable(AppDestination.Home) {
            HomeScreen(
                navigator = { destination -> navController.navigate(destination) }
            )
        }
    }
}
