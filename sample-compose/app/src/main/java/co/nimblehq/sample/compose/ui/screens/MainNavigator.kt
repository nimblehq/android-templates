package co.nimblehq.sample.compose.ui.screens

import androidx.fragment.app.Fragment
import co.nimblehq.sample.compose.R
import co.nimblehq.sample.compose.model.UiModel
import co.nimblehq.sample.compose.ui.base.*
import co.nimblehq.sample.compose.ui.screens.home.HomeComposeFragmentDirections.Companion.actionHomeComposeFragmentToSecondFragment
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        when (event) {
            is NavigationEvent.Second -> navigateToSecond(event.uiModel)
        }
    }

    private fun navigateToSecond(uiModel: UiModel) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            R.id.homeComposeFragment -> navController.navigate(
                actionHomeComposeFragmentToSecondFragment(uiModel)
            )
            else -> unsupportedNavigation()
        }
    }
}
