package co.nimblehq.sample.compose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.composable
import co.nimblehq.sample.compose.ui.models.UiModel
import co.nimblehq.sample.compose.ui.navigate
import co.nimblehq.sample.compose.ui.screens.main.home.HomeScreen
import co.nimblehq.sample.compose.ui.screens.main.second.SecondScreen
import co.nimblehq.sample.compose.ui.screens.main.third.ThirdScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        route = AppDestination.MainNavGraph.route,
        startDestination = MainDestination.Home.destination
    ) {
        composable(destination = MainDestination.Home) {
            HomeScreen(
                navigator = { destination ->
                    navController.navigate(destination, destination.parcelableArgument)
                }
            )
        }

        composable(destination = MainDestination.Second) { backStackEntry ->
            SecondScreen(
                navigator = { destination -> navController.navigate(destination) },
                id = backStackEntry.arguments?.getString(KeyId).orEmpty()
            )
        }

        composable(destination = MainDestination.Third) {
            ThirdScreen(
                navigator = { destination -> navController.navigate(destination) },
                model = navController.previousBackStackEntry?.savedStateHandle?.get<UiModel>(
                    KeyModel
                )
            )
        }
    }
}
