package co.nimblehq.ui.screens

import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import co.nimblehq.R
import co.nimblehq.domain.data.Data
import co.nimblehq.ui.base.NavigationEvent
import co.nimblehq.ui.screens.home.HomeFragmentDirections
import co.nimblehq.ui.screens.second.SecondBundle
import com.nhaarman.mockitokotlin2.verify
import org.amshove.kluent.When
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test
import org.robolectric.util.ReflectionHelpers

class MainNavigatorTest {

    private lateinit var navigator: MainNavigator
    private val mockFragment = mock<Fragment>()
    private val mockNavController = mock<NavController>()
    private val mockDestination = mock<NavDestination>()

    @Before
    fun setup() {
        navigator = MainNavigatorImpl(mockFragment)
        ReflectionHelpers.setField(navigator, "navController", mockNavController)

        When calling mockNavController.currentDestination itReturns mockDestination
    }

    @Test
    fun `Should navigate to Second screen from Home correctly`() {
        When calling mockDestination.id itReturns R.id.homeFragment
        val bundle = SecondBundle(Data(
            "content",
            "url"
        ))
        navigator.navigate(
            NavigationEvent.Second(bundle)
        )

        verify(mockNavController).navigate(
            HomeFragmentDirections.actionHomeFragmentToSecondFragment(bundle)
        )
    }
}
