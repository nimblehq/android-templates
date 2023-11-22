package co.nimblehq.sample.compose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import co.nimblehq.sample.compose.extensions.getThenRemove
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.base.KeyResultOk
import co.nimblehq.sample.compose.ui.composable
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
        composable(destination = MainDestination.Home) { backStackEntry ->
            val isResultOk = backStackEntry.savedStateHandle
                .getThenRemove<Boolean>(KeyResultOk) ?: false
            HomeScreen(
                navigator = { destination ->
                    navController.navigate(destination, destination.parcelableArgument)
                },
                isResultOk = isResultOk,
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
