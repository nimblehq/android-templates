package co.nimblehq.sample.compose.ui.screens.main

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import co.nimblehq.sample.compose.extensions.composable
import co.nimblehq.sample.compose.extensions.getThenRemove
import co.nimblehq.sample.compose.extensions.navigateTo
import co.nimblehq.sample.compose.ui.AppDestination
import co.nimblehq.sample.compose.ui.base.BaseAppDestination
import co.nimblehq.sample.compose.ui.base.KeyResultOk
import co.nimblehq.sample.compose.ui.models.UiModel
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
                isResultOk = isResultOk,
                onNavigateToSecondScreen = {
                    navController.navigateTo(MainDestination.Second.createRoute(it))
                },
                onNavigateToThirdScreen = {
                    navController.navigateTo(MainDestination.Third.addParcel(it))
                }
            )
        }

        composable(destination = MainDestination.Second) { backStackEntry ->
            SecondScreen(
                id = backStackEntry.arguments?.getString(KeyId).orEmpty(),
                onClickUpdate = {
                    navController.navigateTo(
                        BaseAppDestination.Up().apply {
                            put(KeyResultOk, true)
                        }
                    )
                },
            )
        }

        composable(destination = MainDestination.Third) {
            ThirdScreen(
                model = navController.previousBackStackEntry?.savedStateHandle?.get<UiModel>(
                    KeyModel
                )
            )
        }
    }
}
