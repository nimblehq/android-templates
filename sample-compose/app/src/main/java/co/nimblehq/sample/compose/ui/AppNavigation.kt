package co.nimblehq.sample.compose.ui

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.*
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.screens.home.HomeScreen
import co.nimblehq.sample.compose.ui.screens.second.SecondScreen
import co.nimblehq.sample.compose.ui.screens.third.ThirdScreen

@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppDestination.Home.destination
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(destination = AppDestination.Home) {
            HomeScreen(
                navigator = { destination ->
                    navController.navigate(destination, destination.parcel)
                }
            )
        }

        composable(destination = AppDestination.Second) { backStackEntry ->
            SecondScreen(
                navigator = { destination -> navController.navigate(destination) },
                id = backStackEntry.arguments?.getString(KeyId).orEmpty()
            )
        }

        composable(destination = AppDestination.Third) {
            ThirdScreen(
                navigator = { destination -> navController.navigate(destination) },
                model = navController.previousBackStackEntry?.savedStateHandle?.get<UiModel>(KeyModel) ?: UiModel()
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

private fun <T> NavHostController.navigate(appDestination: AppDestination, parcel: Pair<String, T>) {
    when (appDestination) {
        is AppDestination.Up -> navigateUp()
        else -> {
            currentBackStackEntry?.savedStateHandle?.set(parcel.first, parcel.second)
            navigate(route = appDestination.destination)
        }
    }
}
