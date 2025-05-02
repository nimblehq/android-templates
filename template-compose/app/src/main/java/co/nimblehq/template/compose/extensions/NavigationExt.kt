package co.nimblehq.template.compose.extensions

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import co.nimblehq.template.compose.ui.base.BaseAppDestination

private const val NavAnimationDurationInMillis = 300

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlideInLeftTransition() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitSlideOutLeftTransition() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Start,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlideInRightTransition() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitSlideOutRightTransition() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.End,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.enterSlideInUpTransition() =
    slideIntoContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Up,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun AnimatedContentTransitionScope<NavBackStackEntry>.exitSlideOutDownTransition() =
    slideOutOfContainer(
        towards = AnimatedContentTransitionScope.SlideDirection.Down,
        animationSpec = tween(NavAnimationDurationInMillis)
    )

fun NavGraphBuilder.composable(
    destination: BaseAppDestination,
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        enterSlideInLeftTransition()
    },
    exitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        exitSlideOutLeftTransition()
    },
    popEnterTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        enterSlideInRightTransition()
    },
    popExitTransition: (AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        exitSlideOutRightTransition()
    },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = destination.route,
        arguments = destination.arguments,
        deepLinks = deepLinks,
        enterTransition = enterTransition,
        exitTransition = exitTransition,
        popEnterTransition = popEnterTransition,
        popExitTransition = popExitTransition,
        content = content
    )
}

