package co.nimblehq.template.compose.ui.screens

import androidx.fragment.app.Fragment
import co.nimblehq.template.compose.R
import co.nimblehq.template.compose.ui.base.BaseNavigator
import co.nimblehq.template.compose.ui.base.BaseNavigatorImpl
import co.nimblehq.template.compose.ui.base.NavigationEvent
import javax.inject.Inject

interface MainNavigator : BaseNavigator

class MainNavigatorImpl @Inject constructor(
    fragment: Fragment
) : BaseNavigatorImpl(fragment), MainNavigator {

    override val navHostFragmentId = R.id.navHostFragment

    override fun navigate(event: NavigationEvent) {
        val navController = findNavController()
        when (navController?.currentDestination?.id) {
            else -> unsupportedNavigation()
        }
    }
}
