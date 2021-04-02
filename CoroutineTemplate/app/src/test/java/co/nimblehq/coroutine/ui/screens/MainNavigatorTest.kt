package co.nimblehq.coroutine.ui.screens

import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.nimblehq.coroutine.R
import co.nimblehq.coroutine.ui.base.NavigationEvent
import co.nimblehq.coroutine.ui.screens.home.HomeFragmentDirections.Companion.actionHomeFragmentToSecondFragment
import co.nimblehq.coroutine.ui.screens.second.SecondBundle
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.*
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class MainNavigatorTest {

    private lateinit var navigator: MainNavigator
    private val mockActivity = mock<MainActivity>()
    private val mockNavController = mock<NavController>()
    private val mockDestination = mock<NavDestination>()

    @Before
    fun setup() {
        navigator = MainNavigatorImpl(mockActivity)
        ReflectionHelpers.setField(navigator, "navController", mockNavController)

        When calling mockNavController.currentDestination itReturns mockDestination
    }

    @Test
    fun `Should navigate to Second screen from Home screen correctly`() {
        When calling mockDestination.id itReturns R.id.homeFragment
        val bundle = SecondBundle("From home")

        navigator.navigate(
            NavigationEvent.Second(bundle)
        )

        verify(mockNavController).navigate(
            actionHomeFragmentToSecondFragment(bundle)
        )
    }
}
