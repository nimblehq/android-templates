package co.nimblehq.ui.screens

import co.nimblehq.R
import co.nimblehq.ui.base.BaseNavigator
import co.nimblehq.ui.base.BaseNavigatorImpl
import co.nimblehq.ui.base.NavigationEvent
import co.nimblehq.ui.screens.home.HomeFragmentDirections.Companion.actionHomeFragmentToSecondFragment
import co.nimblehq.ui.screens.second.SecondBundle
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    activity: MainActivity
) : BaseNavigatorImpl(activity), MainNavigator {

    override val navHostFragment = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.Second -> navigateToSecond(event.bundle)
        }
    }

    private fun navigateToSecond(bundle: SecondBundle) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeFragment -> navController.navigate(
                actionHomeFragmentToSecondFragment(bundle)
            )
            else -> unsupportedNavigation()
        }
    }

}
